package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.TicketDTO;
import com.example.cinema_system.entity.Session;
import com.example.cinema_system.entity.Ticket;
import com.example.cinema_system.entity.User;
import com.example.cinema_system.exception.SessionNotFoundException;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.repository.SessionRepository;
import com.example.cinema_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TicketMapper implements ClassMapper<Ticket, TicketDTO> {

    private final EnumMapper enumMapper;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    @Override
    public Ticket toEntity(TicketDTO ticketDTO) {
        if (ticketDTO == null) return null;

        Session session = sessionRepository
                .findSessionById(ticketDTO.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Session with id: " + ticketDTO.getSessionId() + "not found"));

        User user = userRepository
                .findUserById(ticketDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + ticketDTO.getUserId() + " not found"));

        Ticket ticket = Ticket.builder()
                .id(ticketDTO.getId())
                .session(session)
                .user(user)
                .hallNumber(ticketDTO.getHallNumber())
                .rowNumber(ticketDTO.getRowNumber())
                .seatNumber(ticketDTO.getSeatNumber())
                .status(enumMapper.stringToTicketStatus(ticketDTO.getStatus()))
                .price(ticketDTO.getPrice())
                .createdAt(ticketDTO.getCreatedAt())
                .build();

        return ticket;
    }

    @Override
    public TicketDTO toDTO(Ticket entity) {
        if (entity == null) return null;

        TicketDTO ticketDTO = TicketDTO.builder()
                .id(entity.getId())
                .sessionId(entity.getSession().getId())
                .userId(entity.getUser().getId())
                .hallNumber(entity.getHallNumber())
                .rowNumber(entity.getRowNumber())
                .seatNumber(entity.getSeatNumber())
                .status(enumMapper.ticketStatusToString(entity.getStatus()))
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .build();

        return ticketDTO;
    }
}
