package com.example.thebra.orderitems;

import com.example.thebra.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/orderItems")
public class OrderItemsController {
    @Autowired
    private OrderItemsService orderItemsService;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @GetMapping("/")
    public List<OrderItems> getAllOrders() {
//        List<OrderItems> orderItems = orderItemsService.findOrderItems();

        return orderItemsRepository.findAll();
    }

    @PostMapping("/")
    public List<OrderItems> createOrderItems(@RequestBody List<OrderItems> orderItemsList) {
        List<OrderItems>   savedOrderItems = new ArrayList<>();
        for (OrderItems orderItems : orderItemsList){
            savedOrderItems.add(orderItemsService.createNewOrderItems(orderItems));
        }
        return savedOrderItems;
    }
}
