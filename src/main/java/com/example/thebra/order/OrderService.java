package com.example.thebra.order;

import com.example.thebra.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(UUID orderId, String paymentStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPaymentStatus(paymentStatus);
            return orderRepository.save(order);
        }
        return null;
    }

}
