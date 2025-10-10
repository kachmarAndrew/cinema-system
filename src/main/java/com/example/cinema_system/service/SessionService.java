package com.example.cinema_system.service;

import com.example.cinema_system.dto.SessionDTO;

import java.util.List;

public interface SessionService {

    SessionDTO createSession(SessionDTO sessionDTO);
    void deleteSession(SessionDTO sessionDTO);

    List<SessionDTO> getSessionsByFilmId(Long filmId);

}
