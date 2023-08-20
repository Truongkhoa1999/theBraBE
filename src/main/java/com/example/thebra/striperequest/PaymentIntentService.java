package com.example.thebra.striperequest;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderRepository;
import com.example.thebra.order.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentIntentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    public String createPaymentIntent(StripeRequest stripeRequest) throws StripeException {
        try {
            Stripe.apiKey = stripeApiKey;
            Map<String, Object> params = new HashMap<>();
            params.put("amount", stripeRequest.getAmount());
            params.put("currency", stripeRequest.getCurrency());

//        Meta data
            Map<String, String> metadata = new HashMap<>();
            metadata.put("orderId", stripeRequest.getOrderId().toString());
            params.put("metadata", metadata);

            PaymentIntent paymentIntent = PaymentIntent.create(params);

//        Update Order records
            String paymentStatus = "paid";
            Order order = orderService.updatePaymentStatus(stripeRequest.getOrderId(), paymentStatus);
            return paymentIntent.getClientSecret();
        } catch (StripeException e){
            e.printStackTrace();
            throw e;
        }

    }

}
