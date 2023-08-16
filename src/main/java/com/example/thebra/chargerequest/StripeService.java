package com.example.thebra.chargerequest;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.*;

import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripeService {

    private final String secretKey = "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";  // Replace with your actual secret key

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() {
        System.out.println("Stripe Secret Key: " + secretKey); // Print the secret key
        Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = secretKey;
        // Create a charge using Stripe API
        Charge charge = Charge.create(Map.of(
                "amount", chargeRequest.getAmount(),
                "currency", chargeRequest.getCurrency(),
                "source", chargeRequest.getStripeToken()
        ));
        return charge;
    }

}
