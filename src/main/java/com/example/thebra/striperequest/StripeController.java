package com.example.thebra.striperequest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderRepository;
import com.example.thebra.order.OrderService;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentLink;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentLinkCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Correct import for the Model class


@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    private final String secretKey = "sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    //    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/createpaymentlink")
    public String createPaymentLink(@RequestBody StripeRequest stripeRequest) {
//    String fixUrl = "https://payreque.st/thebra/";
        String fixUrl = "https://payreque.st/thebra/";
        Order order = orderService.findOrderById(stripeRequest.getOrderId());
        System.out.println(order);
        BigDecimal totalAmountInCents = order.getTotalAmount();
        String currency = stripeRequest.getCurrency();
        String customNote = UUID.randomUUID().toString();
        String dynamicPaymentLink = fixUrl + customNote + "/" + totalAmountInCents;
        order.setPaymentRequestId(customNote);
        orderRepository.save(order);
        return dynamicPaymentLink;
    }
//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
