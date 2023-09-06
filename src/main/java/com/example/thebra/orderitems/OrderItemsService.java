package com.example.thebra.orderitems;

import com.example.thebra.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemsService {
    @Autowired OrderItemsRepository orderItemsRepository;
    public List<OrderItems> findOrderItems() {
        return orderItemsRepository.findAll();
    }
    public OrderItems createNewOrderItems(OrderItems orderItems) {
        return orderItemsRepository.save(orderItems);
    }
    public List<OrderItems>findOrderItemsByOrderId (UUID orderId){return orderItemsRepository.findByOrderId(orderId);}

}
