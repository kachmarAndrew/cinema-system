package com.example.cinema_system.mapper;

import com.example.cinema_system.entity.enums.Genre;
import com.example.cinema_system.entity.enums.Role;
import com.example.cinema_system.entity.enums.TicketStatus;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper { // ternary operator

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
