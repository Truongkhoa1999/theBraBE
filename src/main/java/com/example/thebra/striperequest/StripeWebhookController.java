package com.example.thebra.striperequest;
import com.example.thebra.order.Order;
import com.example.thebra.order.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final OrderRepository orderRepository; // Inject your OrderRepository here

    public StripeWebhookController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded": {
                    // Deserialize the event data to get the PaymentIntent object
                    PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();

                    // Get paymentRequestId from the PaymentIntent metadata
                    String paymentRequestId = paymentIntent.getMetadata().get("paymentRequestId");

                    // Find the order using paymentRequestId and update payment status
                    Order order = orderRepository.findByPaymentRequestId(paymentRequestId);
                    if (order != null && "pending".equals(order.getPaymentStatus())) {
                        order.setPaymentStatus("paid");
                        orderRepository.save(order);
                    }

                    break;
                }
                // ... handle other event types
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            return ResponseEntity.ok("Webhook received and processed successfully.");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Failed to process webhook.");
        }
    }

}
