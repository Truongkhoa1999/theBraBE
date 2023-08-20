package com.example.thebra.striperequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StripeRequest {


    private Long amount;
    private String currency;

    public StripeRequest(Long amount, String currency){
        this.amount = amount;
        this.currency = currency;

    }
}
