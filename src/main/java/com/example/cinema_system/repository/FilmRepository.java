package com.example.cinema_system.repository;

import com.example.cinema_system.model.Film;
import com.example.cinema_system.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository <Film, Long>, JpaSpecificationExecutor<Film> {
    // JpaSpecificationExecutor<Film> it`s dom important for pages in UI(user interface)

    Optional<Film> findFilmById(Long id);
    Optional<Film> findFilmByName(String name);

    List<Film> findAllByGenre(Genre genre);

    boolean existsFilmById(Long id);
    void deleteFilmById(Long id);



}
