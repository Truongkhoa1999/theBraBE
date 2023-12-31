package com.example.thebra.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findByPaymentRequestId(String paymentRequestId);
    List<Order> findByUserId(UUID userId);
}
