package com.example.cinema_system.repository;

import com.example.cinema_system.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findOrderItemById(Long id);

    List<OrderItem> findAllByOrderId(Long orderId);
}
