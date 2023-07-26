package com.example.thebra.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public String processPayment(String token, Long amountInCents, String currency) throws StripeException {
        Stripe.apiKey = "sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(currency)
                .setPaymentMethod(token)
                .setConfirm(true)
                .addPaymentMethodType("card")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        return paymentIntent.getId();
    }
}
