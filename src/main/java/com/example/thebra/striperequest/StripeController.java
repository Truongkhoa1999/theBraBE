package com.example.thebra.striperequest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.thebra.order.Order;
import com.example.thebra.order.OrderRepository;
import com.example.thebra.order.OrderService;
import com.stripe.exception.StripeException;


import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.stripe.param.PaymentIntentCreateParams.PaymentMethodOptions;


//@RestController
//@RequestMapping("/api/v1/stripe")
//public class StripeController {
//    private final String secretKey = "sk_test_51NWsLeKEPicYF8bFnAdKL6QGW7yPJJBLrZLGbswc6VDMWJfH3TdrMnp73Jjn7OsKFTYsiqVOTmtmOfOWztqrGVtl00hA9oXTPH";
//
//    @Autowired
//    private StripeService stripeService;
//
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private OrderRepository orderRepository;
//
//    //    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    @PostMapping("/createpaymentlink")
//    public String createPaymentLink(@RequestBody StripeRequest stripeRequest) {
////    String fixUrl = "https://payreque.st/thebra/";
//        String fixUrl = "https://payreque.st/thebra/";
//        Order order = orderService.findOrderById(stripeRequest.getOrderId());
//        System.out.println(order);
//        BigDecimal totalAmountInCents = order.getTotalAmount();
//        String currency = stripeRequest.getCurrency();
//        String customNote = UUID.randomUUID().toString();
//        String dynamicPaymentLink = fixUrl + customNote + "/" + totalAmountInCents;
//        order.setPaymentRequestId(customNote);
//        orderRepository.save(order);
//        return dynamicPaymentLink;
//    }
////    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//    @ExceptionHandler(StripeException.class)
//    public String handleError(Model model, StripeException ex) {
//        model.addAttribute("error", ex.getMessage());
//        return "result";
//    }
//}
@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/paymentintents")
    public ResponseEntity<?> createPaymentIntent(@RequestBody StripeRequest stripeRequest) {
        try {
            System.out.println(stripeRequest);

            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                    .setAmount(stripeRequest.getAmount())
                    .setCurrency(stripeRequest.getCurrency())
                    .addPaymentMethodType("card")
                    .putMetadata("currency", stripeRequest.getCurrency())
                    .setPaymentMethodOptions(
                            PaymentMethodOptions.builder()
                                    .setCard(
                                            PaymentMethodOptions.Card.builder()
                                                    .setRequestThreeDSecure(PaymentMethodOptions.Card.RequestThreeDSecure.AUTOMATIC)
                                                    .build()
                                    ).build()
                    )
                    .build();


            PaymentIntent paymentIntent = stripeService.createPaymentIntent(params);
            System.out.println(paymentIntent);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create PaymentIntent");
        }
    }
}