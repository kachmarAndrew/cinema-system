package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.OrderDTO;
import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.User;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public abstract class OrderMapper {

    protected final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "items", source = "items")
    public abstract OrderDTO toOrderDTO(Order order);

    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    @Mapping(target = "items", source = "items")
    public abstract Order toOrder(OrderDTO dto);

    @Named("idToUser")
    protected User idToUser(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

}
