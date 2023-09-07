package com.example.thebra.stripewebhook;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.UUID;
import java.util.List;

import javax.mail.*;
import javax.mail.internet.*;

@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {
    @Autowired
    OrderService orderService;
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/payment-intent-succeeded")
    public ResponseEntity<String> handlePaymentIntentSucceeded(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String stripeSignature,
            HttpServletRequest request) {
        System.out.println("hei I found stripe signature, it just fine. I start to do further with the try catch block");

        try {
            Event event = Webhook.constructEvent(payload, stripeSignature, webhookSecret);
            System.out.println("Stripe event has been created");
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                System.out.println(" I found payment intent that hs successded status");

                // Retrieve order_id from metadata
                String orderId = paymentIntent.getMetadata().get("order_id");
                String clientEmail = paymentIntent.getMetadata().get("user_email");
                System.out.println("I found client email" + clientEmail);


                System.out.println(" Your detect orderId in metadata is " + orderId);

                // Update payment status using the service method
                Order updatedOrder = orderService.updatePaymentStatus(UUID.fromString(orderId), "paid");

                if (updatedOrder != null) {
                    System.out.println("Payment status changed to paid");
                    sendThankYouEmail(clientEmail);
                } else {
                    System.out.println("Order not found, payment status not changed");
                }
                return ResponseEntity.ok("Webhook handled successfully");
            }
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Webhook event ignored");
    }

    //    Second feature:
    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    private void sendThankYouEmail(String clientEmail) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        System.out.println("props ready" + props);
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        try {
            System.out.println("Before sending mail");

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clientEmail));
            message.setSubject("Thank You for Your Order");
            message.setText("Thank you for your order at our store. The order ID will be updated to your account as soon as possible. Thus, you can track your delivery process.");

            Transport.send(message);
            System.out.println("Sending mail");

        } catch (MessagingException e) {
            System.out.println(e);

            e.printStackTrace();
        }
    }
}
