package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.FilmDTO;
import com.example.cinema_system.entity.Film;
import com.example.cinema_system.entity.enums.Genre;
import com.example.cinema_system.entity.enums.Language;
import com.example.cinema_system.exception.BadRequestException;
import com.example.cinema_system.exception.FilmNotFoundException;
import com.example.cinema_system.mapper.EnumMapper;
import com.example.cinema_system.mapper.FilmMapper;
import com.example.cinema_system.repository.FilmRepository;
import com.example.cinema_system.repository.specification.FilmSpecification;
import com.example.cinema_system.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final EnumMapper enumMapper;

    @Override
    public Page<FilmDTO> getAllFilms(Pageable pageable, String genre, String language, String search) {
        Genre specificationGenre = enumMapper.stringToGenre(genre);
        Language specificationLanguage = enumMapper.stringToLanguage(language);

        Specification<Film> specification = FilmSpecification.filterByParams(specificationGenre, specificationLanguage, search);

        return filmRepository.findAll(specification, pageable)
                .map(filmMapper::toDTO);
    }

    @Override
    public FilmDTO createFilm(FilmDTO filmDTO) {
        if (filmDTO == null) throw new BadRequestException("Film is null");

        Film film = filmMapper.toEntity(filmDTO);
        Film savedFilm = filmRepository.save(film);

        return filmMapper.toDTO(savedFilm);
    }

    @Override
    public void deleteFilmById(Long id) {
        if (id == null) throw new BadRequestException("Film id is null");
        if (!filmRepository.existsFilmById(id)) throw new FilmNotFoundException("Film with id: " + id + "not found");

        filmRepository.deleteFilmById(id);
    }

    @Override
    public FilmDTO editFilm(Long id, FilmDTO filmDTO) {
        Film existingFilm = filmRepository
                .findFilmById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id: " + id + " not found"));

        existingFilm.setName(filmDTO.getName());
        existingFilm.setGenre(enumMapper.stringToGenre(filmDTO.getGenre()));
        existingFilm.setReleaseAt(filmDTO.getReleaseAt());
        existingFilm.setEndAt(filmDTO.getEndAt());

        Film savedFilm = filmRepository.save(existingFilm);
        return filmMapper.toDTO(savedFilm);
    }

    @Override
    public FilmDTO getFilmById(Long filmId) {
        Film film = filmRepository.findFilmById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id: " + filmId + " not found"));

        return filmMapper.toDTO(film);
    }

    @Override
    public FilmDTO getFilmByName(String filmName) {
        Film film = filmRepository.findFilmByName(filmName)
                .orElseThrow(() -> new FilmNotFoundException("Film with name: " + filmName + " not found"));

        return filmMapper.toDTO(film);

    }

    @Override
    public List<FilmDTO> getAllAvailableFilms() {
        LocalDateTime now = LocalDateTime.now();

        List<FilmDTO> availableFilms = filmRepository
                .findAll().stream()
                .filter(film -> film.getEndAt().isAfter(now))
                .map(filmMapper::toDTO) // map Film to FilmDTO
                .toList();

        return availableFilms;
    }
}
