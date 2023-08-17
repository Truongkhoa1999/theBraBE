package com.example.thebra.striperequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StripeRequest {


    private Long amount;
    private String currency;
    private String stripeToken;
    private UUID orderId;
    public StripeRequest(Long amount, String currency, String stripeToken, UUID orderId){
        this.amount = amount;
        this.currency = currency;
        this.stripeToken = stripeToken;
        this.orderId = orderId;
    }
}
