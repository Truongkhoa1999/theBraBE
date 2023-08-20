package com.example.thebra.striperequest;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paymentitents")
public class PaymentIntentController {
    @Autowired
    private PaymentIntentService paymentIntentService;

    @PostMapping("/")
    public ResponseEntity<String> createPaymentIntent(@RequestBody StripeRequest stripeRequest){
        try{
            String clientSecret = paymentIntentService.createPaymentIntent(stripeRequest);
            return ResponseEntity.ok(clientSecret);
        } catch (StripeException e){
            return  ResponseEntity.badRequest().body("Failed to create payment intent");
        }
    }
}
