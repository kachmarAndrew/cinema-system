package com.example.cinema_system.mapper;


import com.example.cinema_system.dto.OrderDTO;
import com.example.cinema_system.dto.OrderItemDTO;
import com.example.cinema_system.entity.Order;
import com.example.cinema_system.entity.OrderItem;
import com.example.cinema_system.entity.Ticket;
import com.example.cinema_system.exception.OrderNotFoundException;
import com.example.cinema_system.exception.TicketNotFoundException;
import com.example.cinema_system.repository.OrderRepository;
import com.example.cinema_system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper implements ClassMapper<OrderItem, OrderItemDTO> {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;


    @Override
    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) return null;

        Order order = orderRepository
                .findOrderById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderItemDTO.getOrderId() + " not found"));

        Ticket ticket = ticketRepository
                .findTicketById(orderItemDTO.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + orderItemDTO.getTicketId() + " not found"));

        OrderItem orderItem = OrderItem.builder()
                .id(orderItemDTO.getId())
                .order(order)
                .ticket(ticket)
                .price(orderItemDTO.getPrice())
                .build();

        return orderItem;
    }

    @Override
    public OrderItemDTO toDTO(OrderItem entity) {
        if (entity == null) return null;

        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .ticketId(entity.getTicket().getId())
                .build();

        return orderItemDTO;
    }
}
