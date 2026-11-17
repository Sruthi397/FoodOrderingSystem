
package com.example.foodordering.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.foodordering.entity.Food;
import com.example.foodordering.service.FoodService;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "http://localhost:5173")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Add food
    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return foodService.addFood(food);
    }

    // Display all foods
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    // Delete food
    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable int id) {
        foodService.deleteFood(id);
        return "Food deleted successfully";
    }
    @GetMapping("/budget/{amount}")
    public List<Food> getFoodsByBudget(@PathVariable BigDecimal amount) {
        return foodService.findAffordableFoods(amount);
    }
    @PutMapping("/{id}")
    public Food updateFood(
            @PathVariable int id,
            @RequestBody Food food) {

        return foodService.updateFood(id, food);
    }
}
