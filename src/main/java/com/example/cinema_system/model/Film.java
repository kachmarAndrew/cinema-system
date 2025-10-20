package com.example.cinema_system.model;

import com.example.cinema_system.model.enums.Genre;
import com.example.cinema_system.model.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "release_at", nullable = false)
    private LocalDateTime releaseAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Session> sessions; // many to one(One film many sessions);

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
