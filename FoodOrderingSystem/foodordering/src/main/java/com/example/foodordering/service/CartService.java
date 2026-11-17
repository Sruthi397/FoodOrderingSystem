
package com.example.foodordering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.foodordering.entity.CartItem;
import com.example.foodordering.repository.CartRepository;
import com.example.foodordering.repository.FoodRepository;
import com.example.foodordering.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       FoodRepository foodRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    // Add food to cart
    public CartItem addToCart(CartItem item) {

        if (!userRepository.existsById(item.getUserId())) {
            throw new RuntimeException("User not found");
        }

        if (!foodRepository.existsById(item.getFoodId())) {
            throw new RuntimeException("Food not found");
        }

        if (item.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        CartItem existingItem = cartRepository
                .findByUserIdAndFoodId(item.getUserId(), item.getFoodId())
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(
                    existingItem.getQuantity() + item.getQuantity()
            );
            return cartRepository.save(existingItem);
        }

        return cartRepository.save(item);
    }

    // View cart
    public List<CartItem> getCart(int userId) {
        return cartRepository.findByUserId(userId);
    }

    // Update quantity
    public CartItem updateQuantity(int id, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        CartItem item = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);

        return cartRepository.save(item);
    }

    // Remove item
    public void removeFromCart(int id) {

        if (!cartRepository.existsById(id)) {
            throw new RuntimeException("Cart item not found");
        }

        cartRepository.deleteById(id);
    }
}
