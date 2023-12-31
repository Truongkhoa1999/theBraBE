package com.example.thebra.order;

import com.example.thebra.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity(name = "order")
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    //  product info
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private String paymentStatus;
    @Column(nullable = false, columnDefinition = "text")
    private String shippingAddress;
    @Column(nullable = false)
    private String deliveryMethod;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    //    Relationship

    @Column(nullable = true)
    private UUID userId;
    @Column(nullable = true, columnDefinition = "text")
    private String anonymousUserId;
    @Column(nullable = true, columnDefinition = "text")
    private String anonymousUserGmail;
    @Column(nullable = true, columnDefinition = "text")
    private String paymentRequestId;
    @Column(nullable = true, columnDefinition = "text")
    private String country;

    //relationships
    public Order(BigDecimal totalAmount, String paymentStatus, String shippingAddress, String deliveryMethod, Date orderDate, UUID userId, String anonymousUserId, String anonymousUserGmail,String paymentRequestId, String country ) {
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
        this.deliveryMethod = deliveryMethod;
        this.orderDate = orderDate;
        this.userId = userId;
        this.anonymousUserId = anonymousUserId;
        this.anonymousUserGmail =anonymousUserGmail;
        this.paymentRequestId = paymentRequestId;
        this.country = country;
    }
}
