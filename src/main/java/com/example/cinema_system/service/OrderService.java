package com.example.cinema_system.service;

import com.example.cinema_system.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDTO addOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDTO);

    Page<OrderDTO> findOrders(String userEmail, Pageable pageable);

    OrderDTO findOrderById(Long id);

}
