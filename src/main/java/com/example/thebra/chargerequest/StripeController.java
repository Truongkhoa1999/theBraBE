package com.example.thebra.chargerequest;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Correct import for the Model class

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/stripe/charge")
public class StripeController {
    private final String secretKey = "sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";  // Replace with your actual secret key

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public String charge(@RequestBody ChargeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = secretKey;

        // Create a charge using Stripe API
        Charge charge = stripeService.charge(chargeRequest);

        if (charge != null) {
            return "Payment successful!";
        } else {
            return "Payment failed.";
        }
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
