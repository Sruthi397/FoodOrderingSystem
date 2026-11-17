
package com.example.foodordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodordering.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(int userId);
}
