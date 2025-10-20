package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.FilmDTO;
import com.example.cinema_system.model.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmMapper implements ClassMapper<Film, FilmDTO> {

    private final SessionMapper sessionMapper;
    private final ReviewMapper reviewMapper;
    private final EnumMapper enumMapper;

    @Override
    public Film toEntity(FilmDTO filmDTO) {
        if (filmDTO == null) return null;

        Film film = Film.builder()
                .id(filmDTO.getId())
                .name(filmDTO.getName())
                .genre(enumMapper.stringToGenre(filmDTO.getGenre()))
                .language(enumMapper.stringToLanguage(filmDTO.getLanguage()))
                .releaseAt(filmDTO.getReleaseAt())
                .endAt(filmDTO.getEndAt())
                .sessions(filmDTO.getSessions().stream()
                        .map(sessionMapper::toEntity)
                        .toList())
                .reviews(filmDTO.getReviews().stream()
                        .map(reviewMapper::toEntity)
                        .toList())
                .build();

        return film;
    }

    @Override
    public FilmDTO toDTO(Film entity) {
        if (entity == null) return null;

        FilmDTO filmDTO = FilmDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .genre(enumMapper.genreToString(entity.getGenre()))
                .language(enumMapper.languageToString(entity.getLanguage()))
                .releaseAt(entity.getReleaseAt())
                .endAt(entity.getEndAt())
                .sessions(entity.getSessions().stream()
                        .map(sessionMapper::toDTO)
                        .toList())
                .reviews(entity.getReviews().stream()
                        .map(reviewMapper::toDTO)
                        .toList())
                .build();

        return filmDTO;
    }
}

