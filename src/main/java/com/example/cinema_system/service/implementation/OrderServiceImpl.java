package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.OrderDTO;
import com.example.cinema_system.model.Order;
import com.example.cinema_system.model.User;
import com.example.cinema_system.exception.BadRequestException;
import com.example.cinema_system.exception.OrderNotFoundException;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.mapper.OrderMapper;
import com.example.cinema_system.repository.OrderItemRepository;
import com.example.cinema_system.repository.OrderRepository;
import com.example.cinema_system.repository.UserRepository;
import com.example.cinema_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        if (orderDTO == null) throw new BadRequestException("order is null");

        Order order = orderMapper.toEntity(orderDTO);
        orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order savedOrder = orderRepository
                .findOrderById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderDTO.getId() + " not found"));

        Order order = orderMapper.toEntity(orderDTO);

        if (order.getItems() != null && !order.getItems().isEmpty()) {
            order.getItems().forEach(item -> {
                item.setOrder(savedOrder);
                orderItemRepository.save(item);
            });
        }

        return orderMapper.toDTO(order);
    }

    @Override
    public Page<OrderDTO> findOrders(String userEmail, Pageable pageable) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("user.not_found"));

        return orderRepository
                .findAllByUser(user, pageable)
                .map(orderMapper::toDTO);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository
                .findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found"));

        return orderMapper.toDTO(order);
    }
}
