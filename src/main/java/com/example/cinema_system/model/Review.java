package com.example.cinema_system.model;

import com.example.cinema_system.model.enums.FilmAssessment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "film_assessment", nullable = false)
    private FilmAssessment filmAssessment;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "description", nullable = false)
    private String description;
}
