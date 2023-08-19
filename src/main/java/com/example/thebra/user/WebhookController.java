package com.example.thebra.user;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderRepository;
import com.example.thebra.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@RestController
public class WebhookController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/customWebhook")
    public ResponseEntity<String> handleWebhook(@RequestBody JsonNode payload) {
        try {
            String objectType = payload.get("object").asText();
            if ("payment_intent".equals(objectType)) {
                String eventId = payload.get("id").asText();
                String paymentId = payload.get("id").asText();
                String paymentStatus = payload.get("status").asText();

                if ("succeeded".equals(paymentStatus)) {
                    // Find the order by paymentId (assuming you have the paymentId associated with the order)
                    Order order = orderService.findOrderById(UUID.fromString(paymentId));

                    if (order != null) {
                        // Update the payment status and save the order
                        order.setPaymentStatus(paymentStatus);
                        orderRepository.save(order);
                    }
                }
            }

            return ResponseEntity.ok("Webhook received and processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process webhook.");
        }
    }
}



