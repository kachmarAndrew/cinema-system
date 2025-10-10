package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.SessionDTO;
import com.example.cinema_system.entity.Film;
import com.example.cinema_system.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {TicketMapper.class, EnumMapper.class})
public abstract class SessionMapper {

    protected final TicketMapper ticketMapper;

    public SessionMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Mapping(source = "film.id", target = "filmId")
    @Mapping(target = "tickets", source = "tickets")
    public abstract SessionDTO toSessionDTO(Session session);

    @Mapping(target = "film", source = "filmId", qualifiedByName = "idToFilm")
    public abstract Session toSession(SessionDTO dto);

    @Named("idToFilm")
    protected Film idToFilm(Long id) {
        if (id == null) return null;
        Film film = new Film();
        film.setId(id);
        return film;
    }
}
