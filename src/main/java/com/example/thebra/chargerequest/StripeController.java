package com.example.thebra.chargerequest;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Correct import for the Model class


@RestController
@RequestMapping("api/v1/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public String charge(@RequestBody ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = stripeService.charge(chargeRequest);
        if (charge != null) {
            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
        }     else{
            model.addAttribute("error", "Payment failed. Please try again.");
            return "result";
        }


    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
