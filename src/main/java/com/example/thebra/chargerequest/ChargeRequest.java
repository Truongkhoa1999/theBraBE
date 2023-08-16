package com.example.thebra.chargerequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }
    private Long amount;
    private Currency currency;
    private String stripeToken;
//    private UUID orderId;
    public ChargeRequest (Long amount, Currency currency, String stripeToken){
        this.amount = amount;
        this.currency = currency;
        this.stripeToken = stripeToken;
    }
}
