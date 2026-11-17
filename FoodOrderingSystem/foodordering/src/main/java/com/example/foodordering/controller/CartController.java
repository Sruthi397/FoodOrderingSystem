
package com.example.foodordering.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.foodordering.entity.CartItem;
import com.example.foodordering.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add food to cart
    @PostMapping("/add")
    public CartItem addToCart(@RequestBody CartItem item) {
        return cartService.addToCart(item);
    }

    // View cart
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable int userId) {
        return cartService.getCart(userId);
    }

    // Update quantity
    @PutMapping("/update/{id}/{quantity}")
    public CartItem updateQuantity(
            @PathVariable int id,
            @PathVariable int quantity) {
        return cartService.updateQuantity(id, quantity);
    }

    // Remove item
    @DeleteMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartService.removeFromCart(id);
        return "Item removed from cart successfully";
    }
}
