package com.example.thebra.striperequest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class StripeService {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;


    public PaymentIntent createPaymentIntent(PaymentIntentCreateParams params) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        return PaymentIntent.create(params);
    }
}
