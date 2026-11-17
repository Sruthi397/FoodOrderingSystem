package com.example.foodordering.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodordering.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findByPriceLessThanEqualAndAvailableTrue(BigDecimal budget);
}