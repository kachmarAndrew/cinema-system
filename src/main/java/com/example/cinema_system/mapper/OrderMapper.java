package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.OrderDTO;
import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.OrderItem;
import com.example.cinema_system.entity.User;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.repository.OrderItemRepository;
import com.example.cinema_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderMapper implements ClassMapper<Order, OrderDTO> {

    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) return null;

        User user = userRepository
                .findUserById(orderDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + orderDTO.getUserId() + " not found"));

        Order order = Order.builder()
                .id(orderDTO.getId())
                .user(user)
                .total(orderDTO.getTotal())
                .paidAt(orderDTO.getPaidAt())
                .items(orderDTO.getItems().stream()
                        .map(orderItemMapper::toEntity)
                        .toList())
                .build();

        return order;

    }

    @Override
    public OrderDTO toDTO(Order entity) {
        if (entity == null) return null;

        OrderDTO orderDTO = OrderDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .total(entity.getTotal())
                .paidAt(entity.getPaidAt())
                .items(entity.getItems().stream()
                        .map(orderItemMapper::toDTO)
                        .toList())
                .build();
    }
}
