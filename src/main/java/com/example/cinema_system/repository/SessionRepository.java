package com.example.cinema_system.repository;

import com.example.cinema_system.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findSessionById(Long id);

    List<Session> findAllByFilmId(Long filmId);
}
