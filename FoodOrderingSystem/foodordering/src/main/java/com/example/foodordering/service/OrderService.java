
package com.example.foodordering.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.foodordering.entity.CartItem;
import com.example.foodordering.entity.Food;
import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderItem;
import com.example.foodordering.repository.CartRepository;
import com.example.foodordering.repository.FoodRepository;
import com.example.foodordering.repository.OrderRepository;
import com.example.foodordering.repository.OrderItemRepository;
import com.example.foodordering.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        FoodRepository foodRepository,
                        UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    // Place order for entire cart
    @Transactional
    public Order placeOrder(int userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            Food food = foodRepository.findById(cartItem.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            if (!food.isAvailable()) {
                throw new RuntimeException(food.getName() + " is unavailable");
            }

            BigDecimal itemTotal = food.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            total = total.add(itemTotal);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(45));

        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {

            Food food = foodRepository.findById(cartItem.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setFoodId(food.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(food.getPrice());

            orderItemRepository.save(orderItem);
        }

        cartRepository.deleteByUserId(userId);

        return savedOrder;
    }

    // Place order for one selected cart item
    @Transactional
    public Order placeSingleItemOrder(int userId, int cartItemId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (cartItem.getUserId() != userId) {
            throw new RuntimeException("This cart item does not belong to you");
        }

        Food food = foodRepository.findById(cartItem.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (!food.isAvailable()) {
            throw new RuntimeException(food.getName() + " is unavailable");
        }

        BigDecimal total = food.getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(45));

        Order savedOrder = orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(savedOrder.getId());
        orderItem.setFoodId(food.getId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(food.getPrice());

        orderItemRepository.save(orderItem);

        // Remove only the selected item from cart
        cartRepository.delete(cartItem);

        return savedOrder;
    }

    // View order history
    public List<Order> getOrderHistory(int userId) {
        return orderRepository.findByUserId(userId);
    }

    // View items in an order
    public List<OrderItem> getOrderItems(int orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Update order status
    public Order updateOrderStatus(int orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!status.equals("PLACED")
                && !status.equals("PREPARING")
                && !status.equals("OUT_FOR_DELIVERY")
                && !status.equals("DELIVERED")
                && !status.equals("CANCELLED")) {

            throw new RuntimeException("Invalid order status");
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }
}
