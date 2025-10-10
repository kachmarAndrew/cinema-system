package com.example.cinema_system.service;

import com.example.cinema_system.dto.ReviewDTO;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);
    void deleteReview(Long reviewId);

}
