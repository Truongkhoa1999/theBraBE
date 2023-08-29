//package com.example.thebra.stripewebhook;
//import com.example.thebra.order.Order;
//import com.example.thebra.order.OrderService;
//import com.stripe.exception.SignatureVerificationException;
//import com.stripe.model.Event;
//import com.stripe.model.PaymentIntent;
//import com.stripe.net.Webhook;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.UUID;
//import java.util.List;
//
//@RestController
//@RequestMapping("/webhooks")
//public class StripeWebhookController {
//    @Autowired
//    OrderService orderService;
//    private String webhookSecret = "whsec_VLM1U4dOSFEJZbbcIDrXCxuFgUkv9SYE";
//    @PostMapping("/payment-intent-succeeded")
//    public ResponseEntity<String> handlePaymentIntentSucceeded(
//            @RequestBody String payload,
//            @RequestHeader("Stripe-Signature") String stripeSignature,
//            HttpServletRequest request) {
//        System.out.println("hei I found stripe signature, it just fine. I start to do further with the try catch block");
//
//        try {
//            Event event = Webhook.constructEvent(payload, stripeSignature, webhookSecret);
//            if ("payment_intent.succeeded".equals(event.getType())) {
//                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
//                System.out.println(" I found payment intent that hs successded status");
//
//                // Retrieve order_id from metadata
//                String orderId = paymentIntent.getMetadata().get("order_id");
//                System.out.println(" I found  the orderid in pevious payment intent");
//
//                Order detectOrder = orderService.findOrderById(UUID.fromString(orderId));
//                if (detectOrder != null) {
//                    if ("pending".equals(detectOrder.getPaymentStatus())) {
//                        detectOrder.setPaymentStatus("paid");
//                        System.out.println("Payment status changed to paid");
//                    }
//                } else {
//                    System.out.println("Order not found, payment status not changed");
//                }
//                return ResponseEntity.ok("Webhook handled successfully");
//            }
//        } catch (SignatureVerificationException e) {
//            // Invalid signature
//            System.out.println(e);
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            // Handle other exceptions
//            System.out.println(e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        return ResponseEntity.ok("Webhook event ignored");
//    }
//
//}
