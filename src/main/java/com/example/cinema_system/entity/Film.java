package com.example.cinema_system.entity;

import com.example.cinema_system.entity.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "films")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "release_at", nullable = false)
    private LocalDateTime releaseAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Session> sessions; // many to one(One film many sessions);

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
