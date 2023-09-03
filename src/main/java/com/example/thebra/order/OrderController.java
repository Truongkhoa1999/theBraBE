package com.example.thebra.order;

import com.example.thebra.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private JwtUtils jwtUtils;

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

    @GetMapping("/myOrders")
    public ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("Authorization") String authorizationHeader) {
        String secret = jwtUtils.getSecret();
        String jwtToken = authorizationHeader.replace("Bearer", "");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtUtils.getSecret())
                    .parseClaimsJws(jwtToken);
            String userId = claims.getBody().get("userId", String.class);
            List<Order> orders = orderService.findOrderByUserId(UUID.fromString(userId));
            return new ResponseEntity<>(orders, HttpStatus.OK);


        } catch (Exception e) {
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/")
    public Order createorder(@RequestBody Order order) {
        return orderService.createNewOrder(order);
    }

    @PostMapping("/forNonUser")
    public Order createorderForNonUser(@RequestBody Order order) {
        if (order.getUserId() == null) {
            String anonymousUserId = UUID.randomUUID().toString() + "_anonymous";
            order.setAnonymousUserId(anonymousUserId);
        }
        return orderService.createNewOrder(order);
    }
}
