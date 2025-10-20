package com.example.cinema_system.repository.specification;

import com.example.cinema_system.model.Film;
import com.example.cinema_system.model.enums.Genre;
import com.example.cinema_system.model.enums.Language;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecification {

    public static Specification<Film> filterByParams(Genre genre, Language language, String search) {
        return Specification.allOf(
                hasGenre(genre),
                hasLanguage(language),
                hasSearch(search)
        );
    }

    private static Specification<Film> hasGenre(Genre genre) {
        return (root, query, criteriaBuilder) -> {
            if (genre == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("genre"), genre);
        };
    }

    private static Specification<Film> hasLanguage(Language language) {
        return (root, query, criteriaBuilder) -> {
            if (language == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("language"), language);
        };
    }

    private static Specification<Film> hasSearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) return  criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
        };
    }

}
