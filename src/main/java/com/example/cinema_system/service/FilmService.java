package com.example.cinema_system.service;

import com.example.cinema_system.dto.FilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmService {

    Page<FilmDTO> getAllFilms(Pageable pageable, String genre, String language, String search);
    FilmDTO createFilm(FilmDTO filmDTO);
    void deleteFilmById(Long id);

    FilmDTO editFilm(Long id, FilmDTO filmDTO);

    FilmDTO getFilmById(Long filmId);
    FilmDTO getFilmByName(String filmName);

    List<FilmDTO> getAllAvailableFilms();

}
