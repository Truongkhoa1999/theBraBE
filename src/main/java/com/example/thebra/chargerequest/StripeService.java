package com.example.thebra.chargerequest;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.*;

import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;


import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;
    @Value("${STRIPE_SECRET_KEY}") // Inject your Stripe secret key from application.properties
    private String stripeSecretKey;
    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() {
        System.out.println("Stripe Secret Key: " + secretKey); // Print the secret key
        Stripe.apiKey = secretKey;
    }

    //    public Charge charge(ChargeRequest chargeRequest) {
//        try {
//            System.out.println("Total Amount: " + chargeRequest.getAmount()); // Add this line for logging
//
//            Map<String, Object> chargeParams = new HashMap<>();
//            chargeParams.put("amount", chargeRequest.getAmount());
//            chargeParams.put("currency", chargeRequest.getCurrency());
//            chargeParams.put("source", chargeRequest.getStripeToken());
//            return Charge.create(chargeParams);
//        } catch (  StripeException e  ) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public Charge charge(ChargeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        // Create a charge using Stripe API
        Charge charge = Charge.create(Map.of(
                "amount", chargeRequest.getAmount(),
                "currency", chargeRequest.getCurrency(),
                "source", chargeRequest.getStripeToken()
        ));
        UUID orderId = chargeRequest.getOrderId();
        Order order = orderService.findOrderById(orderId);
        order.setPaymentStatus("Paid");
        return charge;
    }

}
