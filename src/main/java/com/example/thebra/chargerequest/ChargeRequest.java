package com.example.thebra.chargerequest;

import lombok.Data;

import java.util.UUID;

@Data
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }
    private Long amount;
    private Currency currency;
    private String stripeToken;
    private UUID id;
}
