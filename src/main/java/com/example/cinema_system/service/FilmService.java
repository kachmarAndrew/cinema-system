package com.example.cinema_system.service;

import com.example.cinema_system.dto.FilmDTO;

import java.util.List;

public interface FilmService {

    FilmDTO createFilm(FilmDTO filmDTO);
    void deleteFilm(FilmDTO filmDTO);

    FilmDTO editFilm(FilmDTO filmDTO);

    FilmDTO getFilmById(Long filmId);
    FilmDTO getFilmByName(String filmName);

    List<FilmDTO> getAllAvailableFilms();

}
