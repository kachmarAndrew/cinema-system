package com.example.cinema_system.repository;

import com.example.cinema_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findReviewById(Long id);

    List<Review> findAllByFilmId(Long filmId);
}
