package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.OrderItemDTO;
import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.OrderItem;
import com.example.cinema_system.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public abstract class OrderItemMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "ticket.id", target = "ticketId")
    public abstract OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    @Mapping(target = "ticket", source = "ticketId", qualifiedByName = "idToTicket")
    public abstract OrderItem toOrderItem(OrderItemDTO orderItemDTO);

    @Named("idToOrder")
    protected Order idToOrder(Long id){
        if (id == null) return null;
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("idToTicket")
    protected Ticket idToTicket(Long id) {
        if (id == null) return null;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
