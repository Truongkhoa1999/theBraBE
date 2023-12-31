package com.example.thebra.orderitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, UUID> {
    public List<OrderItems> findByOrderId(UUID orderId );
}
