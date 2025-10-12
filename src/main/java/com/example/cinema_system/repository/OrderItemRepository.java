package com.example.cinema_system.repository;

import com.example.cinema_system.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findOrderItemById(Long id);

    List<OrderItem> findAllByOrderId(Long orderId);
}
