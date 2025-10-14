package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.TicketDTO;
import com.example.cinema_system.entity.Session;
import com.example.cinema_system.entity.Ticket;
import com.example.cinema_system.entity.User;
import com.example.cinema_system.entity.enums.TicketStatus;
import com.example.cinema_system.exception.BadRequestException;
import com.example.cinema_system.exception.TicketNotFoundException;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.mapper.TicketMapper;
import com.example.cinema_system.repository.TicketRepository;
import com.example.cinema_system.repository.UserRepository;
import com.example.cinema_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public void bookTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null) throw new BadRequestException("Ticket is null");

        User user = userRepository
                .findUserById(ticketDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + ticketDTO.getUserId() + " not found"));

        Ticket ticket = ticketRepository
                .findTicketById(ticketDTO.getId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketDTO.getId() + " not found"));

        if (ticket.getStatus() == TicketStatus.SOLD)
            throw new BadRequestException("This ticket is already book from another user");

        BigDecimal price = ticket.getPrice();
        if (user.getBalance().compareTo(price) < 0)
            throw new BadRequestException("You don`t have enough money to book/buy this ticket");

        user.setBalance(user.getBalance().subtract(price));
        ticket.setStatus(TicketStatus.SOLD);
        ticket.setUser(user);

        userRepository.save(user);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void cancelBookingTicket(Long ticketId) {
        if (ticketId == null) throw new BadRequestException("Ticket is null");

        Ticket ticket = ticketRepository
                .findTicketById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));

        User user = userRepository
                .findUserById(ticket.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + ticket.getUser().getId() + " not found"));

        Session session = ticket.getSession();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startAt = session.getStartAt();

        if (!startAt.isAfter(now.plusHours(24))) {
            throw new BadRequestException("Cannot cancel ticket less than 24 hours before session start");
        }

        user.setBalance(user.getBalance().add(ticket.getPrice()));
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setUser(null);

        userRepository.save(user);
        ticketRepository.save(ticket);
    }

    @Override
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        return ticketRepository
                .findAllByUserId(userId).stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Override
    public List<TicketDTO> getTicketsByStatus(TicketStatus ticketStatus) {
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getStatus() == ticketStatus)
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Override
    public List<TicketDTO> getTicketsBySessionId(Long sessionId) {
        return ticketRepository
                .findAllBySessionId(sessionId).stream()
                .map(ticketMapper::toDTO)
                .toList();
    }
}
