package com.example.thebra.orderitems;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "orderItems")
@Table(name = "orderItems")
@Data
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    //    orderItem info
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = true)
    private UUID orderId;


    public OrderItems(int quantity, String size, UUID productId, UUID orderId) {
        this.quantity = quantity;
        this.size = size;
        this.productId = productId;
        this.orderId = orderId;
    }
//    public OrderItems() {
//        // Default constructor
//    }
}
