package com.example.thebra.striperequest;

import com.example.thebra.order.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Correct import for the Model class


@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    private final String secretKey = "pk_test_51NWsLeKEPicYF8bFJDh3qQrRuRi61NgIGgzcm2C2aE1p6P8THLoqQYDH1VP0Kccl7BVdz8Pc77Fz9Z2R6riDlAjS00UT89BzRl";

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public String charge(@RequestBody StripeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = secretKey;

        // Create a charge using Stripe API
        Charge charge = stripeService.charge(chargeRequest);

        if (charge != null) {
            return "Payment successful!";
        } else {
            return "Payment failed.";
        }
    }
    @PostMapping("/create-payment-link")
    public ResponseEntity<String> createPaymentLink(@RequestBody StripeRequest stripeRequest) {
        // Set your Stripe secret key
        Stripe.apiKey = secretKey;

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(stripeRequest.getAmount())
                    .setCurrency(stripeRequest.getCurrency())
                    .setDescription("Order #" + stripeRequest.getOrderId())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            String clientSecret = paymentIntent.getClientSecret();
            return ResponseEntity.ok(clientSecret);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
