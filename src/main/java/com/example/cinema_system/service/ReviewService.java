package com.example.cinema_system.service;

import com.example.cinema_system.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);
    void deleteReview(Long reviewId);

    List<ReviewDTO> getAllReviewsByFilmId(Long filmId);

}
