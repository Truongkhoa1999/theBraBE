package com.example.thebra.stripe;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stripe")
public class StripeController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody StripeRequest stripeRequest) {
        try {
            String paymentIntentId = stripeService.processPayment(stripeRequest.getToken(), stripeRequest.getAmount(), stripeRequest.getCurrency());
            return ResponseEntity.ok(paymentIntentId);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment processing failed: " + e.getMessage());
        }
    }
}
