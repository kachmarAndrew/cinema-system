package com.example.cinema_system.repository;

import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderById(Long id);

    List<Order> findAllByUserId(Long userId);

    Page<Order> findAllByUser(User user, Pageable pageable);

}
