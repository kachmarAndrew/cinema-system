package com.example.cinema_system.service;

import com.example.cinema_system.dto.TicketDTO;
import com.example.cinema_system.model.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    void bookTicket(TicketDTO ticketDTO);
    void cancelBookingTicket(Long ticketId);

    List<TicketDTO> getTicketsByUserId(Long userId);
    List<TicketDTO> getTicketsByStatus(TicketStatus ticketStatus);
    List<TicketDTO> getTicketsBySessionId(Long sessionId);

}
