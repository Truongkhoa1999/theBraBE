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
                String paymentRequestId = payload.get("paymentRequestId").asText();
                String paymentStatus = payload.get("status").asText();

                if ("succeeded".equals(paymentStatus)) {
                    Order order = orderRepository.findByPaymentRequestId(paymentRequestId);

                    if (order != null && "pending".equals(order.getPaymentStatus())) {
                        // Update the payment status and save the order
                        order.setPaymentStatus("paid");
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



