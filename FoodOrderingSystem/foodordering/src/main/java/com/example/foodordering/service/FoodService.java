
package com.example.foodordering.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.foodordering.entity.Food;
import com.example.foodordering.repository.FoodRepository;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // Add food
    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    // Display all food items
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // Delete food
    public void deleteFood(int id) {
        foodRepository.deleteById(id);
    }
    public List<Food> findAffordableFoods(BigDecimal budget) {
        return foodRepository.findByPriceLessThanEqualAndAvailableTrue(budget);
    }
    public Food updateFood(int id, Food newFood) {

        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        existingFood.setName(newFood.getName());
        existingFood.setDescription(newFood.getDescription());
        existingFood.setPrice(newFood.getPrice());
        existingFood.setCategory(newFood.getCategory());
        existingFood.setImageUrl(newFood.getImageUrl());
        existingFood.setAvailable(newFood.isAvailable());

        return foodRepository.save(existingFood);
    }
}
