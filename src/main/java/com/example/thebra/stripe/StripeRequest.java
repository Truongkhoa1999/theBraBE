package com.example.thebra.stripe;

import lombok.Data;

@Data
public class StripeRequest {
    private String token;
    private Long amount;
    private String currency;
}
