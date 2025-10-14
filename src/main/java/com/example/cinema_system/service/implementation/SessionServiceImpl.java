package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.SessionDTO;
import com.example.cinema_system.entity.Film;
import com.example.cinema_system.entity.Session;
import com.example.cinema_system.exception.BadRequestException;
import com.example.cinema_system.exception.FilmNotFoundException;
import com.example.cinema_system.exception.SessionNotFoundException;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.mapper.SessionMapper;
import com.example.cinema_system.repository.FilmRepository;
import com.example.cinema_system.repository.SessionRepository;
import com.example.cinema_system.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final FilmRepository filmRepository;
    private final SessionMapper sessionMapper;

    @Override
    @Transactional
    public SessionDTO createSession(SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            throw new BadRequestException("Session data is null");
        }

        Film film = filmRepository.findById(sessionDTO.getFilmId())
                .orElseThrow(() -> new FilmNotFoundException("Film with id " + sessionDTO.getFilmId() + " not found"));

        Session session = sessionMapper.toEntity(sessionDTO);
        session.setFilm(film);

        // приклад простої валідації — сеанс не може бути у минулому
        if (session.getStartAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Session start time cannot be in the past");
        }

        Session savedSession = sessionRepository.save(session);
        return sessionMapper.toDTO(savedSession);
    }

    @Override
    @Transactional
    public void deleteSession(SessionDTO sessionDTO) {
        if (sessionDTO == null || sessionDTO.getId() == null) {
            throw new BadRequestException("Session id is null");
        }

        Session session = sessionRepository.findById(sessionDTO.getId())
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        sessionRepository.delete(session);
    }

    @Override
    @Transactional
    public SessionDTO editSession(Long id, SessionDTO sessionDTO) {
        Session existingSession = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session with id " + id + " not found"));

        existingSession.setStartAt(sessionDTO.getStartAt());
        existingSession.setEndsAt(sessionDTO.getEndAt());

        // якщо треба змінити фільм для сесії
        if (sessionDTO.getFilmId() != null) {
            Film film = filmRepository.findById(sessionDTO.getFilmId())
                    .orElseThrow(() -> new FilmNotFoundException("Film with id " + sessionDTO.getFilmId() + " not found"));
            existingSession.setFilm(film);
        }

        Session updated = sessionRepository.save(existingSession);
        return sessionMapper.toDTO(updated);
    }

    @Override
    public List<SessionDTO> getSessionsByFilmId(Long filmId) {
        if (filmId == null) {
            throw new BadRequestException("Film id is null");
        }

        List<Session> sessions = sessionRepository.findAllByFilmId(filmId);

        return sessions.stream()
                .map(sessionMapper::toDTO)
                .toList();
    }

    public Page<SessionDTO> findSessionsByFilmIdPage(Long filmId, Pageable pageable) {
        Film film = filmRepository
                .findFilmById(filmId)
                .orElseThrow(() -> new UserNotFoundException("Film with id: " + filmId + " not found"));

        return sessionRepository
                .findAllByFilm(film, pageable)
                .map(sessionMapper::toDTO);
    }

}
