package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.ReviewDTO;
import com.example.cinema_system.entity.Film;
import com.example.cinema_system.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    // Entity -> DTO
    @Mapping(source = "film.id", target = "filmId")
    ReviewDTO toReviewDTO(Review review);

    // DTO -> Entity
    @Mapping(source = "filmId", target = "film", qualifiedByName = "mapFilmIdToFilm")
    Review toReview(ReviewDTO reviewDTO);

    @Named("mapFilmIdToFilm")
    default Film mapFilmIdToFilm(Long filmId) {
        if (filmId == null) return null;
        Film film = new Film();
        film.setId(filmId);
        return film;
    }
}
