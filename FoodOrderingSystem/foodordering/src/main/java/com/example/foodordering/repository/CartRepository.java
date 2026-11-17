package com.example.foodordering.repository;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodordering.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserId(int userId);
    Optional<CartItem> findByUserIdAndFoodId(int userId, int foodId);
    void deleteByUserId(int userId);
}
