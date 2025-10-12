package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.ReviewDTO;
import com.example.cinema_system.entity.Film;
import com.example.cinema_system.entity.Review;
import com.example.cinema_system.exception.FilmNotFoundException;
import com.example.cinema_system.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper implements ClassMapper<Review, ReviewDTO> {

    private final FilmRepository filmRepository;


    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) return null;

        Film film = filmRepository
                .findFilmById(reviewDTO.getFilmId())
                .orElseThrow(() -> new FilmNotFoundException("Film with id: " + reviewDTO.getFilmId() + " not found"));

        Review review = Review.builder()
                .id(reviewDTO.getId())
                .userName(reviewDTO.getUserName())
                .film(film)
                .description(reviewDTO.getDescription())
                .build();

        return review;
    }

    @Override
    public ReviewDTO toDTO(Review entity) {
        if (entity == null) return null;

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .filmId(entity.getFilm().getId())
                .description(entity.getDescription())
                .build();

        return reviewDTO;

    }
}
