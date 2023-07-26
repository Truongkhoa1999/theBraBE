package com.example.thebra.order;

import com.example.thebra.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    public Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }


}
