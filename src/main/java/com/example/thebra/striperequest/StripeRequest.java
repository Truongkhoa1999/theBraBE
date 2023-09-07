package com.example.thebra.striperequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StripeRequest {


    private Long amount;
    private String currency;
    private String orderId;
    private String userEmail;

    public StripeRequest(Long amount, String currency, String userEmail){
        this.amount = amount;
        this.currency = currency;
        this.userEmail = userEmail;

    }
}
