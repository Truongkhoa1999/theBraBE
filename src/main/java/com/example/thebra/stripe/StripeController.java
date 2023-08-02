//package com.example.thebra.stripe;
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Charge;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("api/v1/stripe")
//public class StripeController {
//    @Autowired
//    private StripeService stripeService;
//
//    @PostMapping("/processpayment")
//    public ResponseEntity<String> processPayment(@RequestBody StripeRequest stripeRequest) {
//        System.out.println("Received StripeRequest: " + stripeRequest);
//
//        String token = stripeRequest.getToken();
//        Long amount = stripeRequest.getAmount();
//        String currency = stripeRequest.getCurrency();
//        try {
//            Stripe.apiKey = "sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";
//            String paymentIntentId = stripeService.processPayment(stripeRequest.getToken(), stripeRequest.getAmount(), stripeRequest.getCurrency());
//
//            // Payment processed successfully
//            return ResponseEntity.ok("Payment processed successfully");
//
//        } catch (StripeException e) {
//            e.printStackTrace();
//            // Error processing payment
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment");
//        }
//    }
//
//}
//
//
