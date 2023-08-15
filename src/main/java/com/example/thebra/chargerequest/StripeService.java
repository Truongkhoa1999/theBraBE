package com.example.thebra.chargerequest;

import com.stripe.Stripe;
import com.stripe.exception.*;

import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        System.out.println("Stripe Secret Key: " + secretKey); // Print the secret key
        Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest) {
        try {
            System.out.println("Total Amount: " + chargeRequest.getAmount()); // Add this line for logging

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", chargeRequest.getCurrency());
            chargeParams.put("source", chargeRequest.getStripeToken());
            return Charge.create(chargeParams);
        } catch (  StripeException e  ) {
            e.printStackTrace();
            return null;
        }
    }

}
