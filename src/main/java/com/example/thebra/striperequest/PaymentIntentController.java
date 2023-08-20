package com.example.thebra.striperequest;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/paymentitents")
public class PaymentIntentController {
    @Autowired
    private PaymentIntentService paymentIntentService;

    @PostMapping("/")
    public ResponseEntity<Map<String,String>> createPaymentIntent(@RequestBody StripeRequest stripeRequest){
        try{
            String clientSecret = paymentIntentService.createPaymentIntent(stripeRequest);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("clientSecret",clientSecret);
            return ResponseEntity.ok(responseMap);
        } catch (StripeException e){
            return  ResponseEntity.badRequest().body(Collections.singletonMap("error","Failed to create payment intent"));
        }
    }
}
