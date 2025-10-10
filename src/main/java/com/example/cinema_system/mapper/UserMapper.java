package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {TicketMapper.class, OrderMapper.class, EnumMapper.class})
public abstract class UserMapper {

    protected final TicketMapper ticketMapper;
    protected final OrderMapper orderMapper;
    protected final EnumMapper enumMapper;

    public UserMapper(TicketMapper ticketMapper, OrderMapper orderMapper, EnumMapper enumMapper) {
        this.ticketMapper = ticketMapper;
        this.orderMapper = orderMapper;
        this.enumMapper = enumMapper;
    }

    @Mapping(target = "role", expression = "java(enumMapper.roleToString(user.getRole()))")
    @Mapping(target = "tickets", source = "tickets")
    @Mapping(target = "orders", source = "orders")
    public abstract UserDTO toUserDTO(User user);

    @Mapping(target = "role", expression = "java(enumMapper.stringToRole(userDTO.getRole()))")
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "orders", ignore = true)
    public abstract User toUser(UserDTO userDTO);
}
