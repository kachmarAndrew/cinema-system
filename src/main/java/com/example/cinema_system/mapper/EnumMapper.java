package com.example.cinema_system.mapper;

import com.example.cinema_system.model.enums.*;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper { // ternary operator

    // == Language ==
    public String languageToString(Language language) {
        return language == null ? null : language.name();
    }
    public Language stringToLanguage(String language) {
        return language == null ? null : Language.valueOf(language);
    }

    // == FilmAssessment ==
    public String filmAssessmentToString(FilmAssessment filmAssessment) {
        return filmAssessment == null ? null : filmAssessment.name();
    }
    public FilmAssessment stringToFilmAssessment(String filmAssessment) {
        return filmAssessment == null ? null : FilmAssessment.valueOf(filmAssessment);
    }

    // == Role ==
    public String roleToString(Role role) {
        return role == null ? null : role.name();
    }
    public Role stringToRole(String role) {
        return role == null ? null : Role.valueOf(role);
    }

    // == Genre ==
    public String genreToString(Genre genre) {
        return genre == null ? null : genre.name();
    }
    public Genre stringToGenre(String genre) {
        return genre == null ? null : Genre.valueOf(genre);
    }

    // == TicketStatus ==
    public String ticketStatusToString(TicketStatus ticketStatus) {
        if (ticketStatus == null) {
            return null;
        } else {
            return ticketStatus.name();
        }
    }

    public TicketStatus stringToTicketStatus(String ticketStatus) {
        if (ticketStatus == null) {
            return null;
        } else {
            return TicketStatus.valueOf(ticketStatus);
        }
    }

}
