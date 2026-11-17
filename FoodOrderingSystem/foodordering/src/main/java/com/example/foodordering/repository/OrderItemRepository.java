
package com.example.foodordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodordering.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(int orderId);
}
