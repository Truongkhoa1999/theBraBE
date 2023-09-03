package com.example.thebra.order;

import com.example.thebra.product.Product;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findOrderByUserId(UUID userId){return orderRepository.findByUserId(userId);}

    public Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(UUID orderId, String paymentStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPaymentStatus(paymentStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    @Scheduled(fixedRate = 300000)
    public void updatePaymentStatus() {
        Stripe.apiKey = stripeSecretKey;
        try {
            PaymentIntentListParams params = PaymentIntentListParams.builder().build();
            List<PaymentIntent> paymentIntents = PaymentIntent.list(params).getData();
            for (PaymentIntent paymentIntent : paymentIntents) {
                String orderId = paymentIntent.getMetadata().get("order_id");
                String paymentIntentId = paymentIntent.getId();
                String paymentStatus = paymentIntent.getStatus();
                if ("succeeded".equals(paymentStatus)) {
                    UUID uuidOrderId = UUID.fromString(orderId);
                    Order order = findOrderById(uuidOrderId);
                    if (order != null) {
                        updatePaymentStatus(uuidOrderId, "paid");
                    }
                }
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

}
