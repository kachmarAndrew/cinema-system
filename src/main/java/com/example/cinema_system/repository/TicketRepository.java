package com.example.cinema_system.repository;

import com.example.cinema_system.entity.Ticket;
import org.hibernate.query.criteria.JpaSearchedCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository  extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findTicketById(Long id);

    List<Ticket> findAllBySessionId(Long sessionId);
    List<Ticket> findAllByUserId(Long userId);

}
