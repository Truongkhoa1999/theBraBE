package com.example.thebra.striperequest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class StripeService {

    private String stripeSecretKey ="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";
//    private String stripeSecretKey ="sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";


    public PaymentIntent createPaymentIntent(PaymentIntentCreateParams params) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        return PaymentIntent.create(params);
    }
}
