package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.ReviewDTO;
import com.example.cinema_system.model.Review;
import com.example.cinema_system.exception.BadRequestException;
import com.example.cinema_system.exception.ReviewNotFoundException;
import com.example.cinema_system.mapper.ReviewMapper;
import com.example.cinema_system.repository.ReviewRepository;
import com.example.cinema_system.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        if (reviewDTO == null) throw new BadRequestException("Review is null");
        Review review = reviewMapper.toEntity(reviewDTO);
        reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (reviewId == null) throw new BadRequestException("Review id is null");

        Review review = reviewRepository
                .findReviewById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id: " + reviewId + " not found"));

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewDTO> getAllReviewsByFilmId(Long filmId) {
        return reviewRepository.findAllByFilmId(filmId).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

}
