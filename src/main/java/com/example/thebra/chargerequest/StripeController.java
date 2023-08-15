package com.example.thebra.chargerequest;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Correct import for the Model class

import java.util.UUID;


@RestController
@RequestMapping("api/v1/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/charge")
    public String charge(@RequestBody ChargeRequest chargeRequest, Model model)
            throws StripeException {
        UUID id = chargeRequest.getId();
        Order order = orderService.findOrderById(id);
        order.setPaymentStatus("Pending");
        String returnUrl = "http://localhost:5173/payment?orderId=" + order.getId();
        chargeRequest.setReturnUrl(returnUrl);
//        Make Stripe request
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        String authenticationUrl = stripeService.initiate3DSecure(chargeRequest);

        Charge charge = stripeService.charge(chargeRequest);
        if (charge != null) {
            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
            order.setPaymentStatus("Paid");
            return "result";
        } else {
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
