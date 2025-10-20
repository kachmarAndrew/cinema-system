package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.SessionDTO;
import com.example.cinema_system.model.Film;
import com.example.cinema_system.model.Session;
import com.example.cinema_system.exception.FilmNotFoundException;
import com.example.cinema_system.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper implements ClassMapper<Session, SessionDTO> {

    private final TicketMapper ticketMapper;
    private final FilmRepository filmRepository;

    @Override
    public Session toEntity(SessionDTO sessionDTO) {
        if (sessionDTO == null) return null;

        Film film = filmRepository
                .findFilmById(sessionDTO.getFilmId())
                .orElseThrow(() -> new FilmNotFoundException("Film with id: " + sessionDTO.getFilmId() + " not found"));

        Session session = Session.builder()
                .film(film)
                .quantityOfSeats(sessionDTO.getQuantityOfSeats())
                .startAt(sessionDTO.getStartAt())
                .endsAt(sessionDTO.getEndAt())
                .tickets(sessionDTO.getTickets().stream()
                        .map(ticketMapper::toEntity)
                        .toList())
                .build();

        return session;
    }

    @Override
    public SessionDTO toDTO(Session entity) {
        if (entity == null) return null;

        SessionDTO sessionDTO = SessionDTO.builder()
                .id(entity.getId())
                .filmId(entity.getFilm().getId())
                .quantityOfSeats(entity.getQuantityOfSeats())
                .startAt(entity.getStartAt())
                .endAt(entity.getEndsAt())
                .tickets(entity.getTickets().stream()
                        .map(ticketMapper::toDTO)
                        .toList())
                .build();

        return sessionDTO;
    }
}
