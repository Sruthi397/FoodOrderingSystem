
package com.example.foodordering.controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.foodordering.entity.Order;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderItem;
import com.example.foodordering.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place order
    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable int userId) {
        return orderService.placeOrder(userId);
    }

    // View order history
    @GetMapping("/history/{userId}")
    public List<Order> getOrderHistory(@PathVariable int userId) {
        return orderService.getOrderHistory(userId);
    }

    // View items in an order
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable int orderId) {
        return orderService.getOrderItems(orderId);
    }
    @PutMapping("/status/{orderId}")
    public Order updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam String status) {

        return orderService.updateOrderStatus(orderId, status);
    }
    @PostMapping("/place-single/{userId}/{cartItemId}")
    public Order placeSingleItemOrder(
            @PathVariable int userId,
            @PathVariable int cartItemId) {

        return orderService.placeSingleItemOrder(userId, cartItemId);
    }
}
