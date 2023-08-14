package com.example.thebra.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.findOrders();
        return orders;
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable UUID id) {
        Order order = orderService.findOrderById(id);
        if (order == null) {
            return null;
        }
        return order;
    }

    @PostMapping("/")
    public Order createorder(@RequestBody Order order) {
        return orderService.createNewOrder(order);
    }
    @PostMapping("/forNonUser")
    public Order createorderForNonUser(@RequestBody Order order) {
        return orderService.createNewOrder(order);
    }


}
