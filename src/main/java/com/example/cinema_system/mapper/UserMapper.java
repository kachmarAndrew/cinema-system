package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.Ticket;
import com.example.cinema_system.entity.User;
import com.example.cinema_system.repository.OrderRepository;
import com.example.cinema_system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements ClassMapper<User, UserDTO> {

    private final EnumMapper enumMapper;
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        List<Ticket> tickets = ticketRepository.findAllByUserId(userDTO.getId());
        List<Order> orders = orderRepository.findAllByUserId(userDTO.getId());

        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(enumMapper.stringToRole(userDTO.getRole()))
                .balance(userDTO.getBalance())
                .tickets(tickets)
                .orders(orders)
                .build();

        return user;
    }

    @Override
    public UserDTO toDTO(User entity) {
        if (entity == null) return null;

        UserDTO userDTO = UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(enumMapper.roleToString(entity.getRole()))
                .balance(entity.getBalance())
                .build();

        return userDTO;
    }
}
