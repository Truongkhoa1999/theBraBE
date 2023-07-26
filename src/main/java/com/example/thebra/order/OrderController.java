package com.example.thebra.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  OrderService orderService;

    @GetMapping("/")
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.findOrders();
        return orders;
    }
    @PostMapping("/")
    public Order createorder(@RequestBody Order order) {
        return orderService.createNewOrder(order);
    }

}
