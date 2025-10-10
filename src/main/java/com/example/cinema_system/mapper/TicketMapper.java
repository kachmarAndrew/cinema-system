package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.TicketDTO;
import com.example.cinema_system.entity.Session;
import com.example.cinema_system.entity.Ticket;
import com.example.cinema_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {EnumMapper.class})
public abstract class TicketMapper {

    protected final EnumMapper enumMapper;

    public TicketMapper(EnumMapper enumMapper) {
        this.enumMapper = enumMapper;
    }

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "ticketStatus", expression = "java(enumMapper.ticketStatusToString(ticket.getStatus()))")
    public abstract TicketDTO toTicketDTO(Ticket ticket);

    @Mapping(target = "session", source = "sessionId", qualifiedByName = "idToSession")
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    @Mapping(target = "status", expression = "java(enumMapper.stringToTicketStatus(ticketDTO.getTicketStatus()))")
    public abstract Ticket toTicket(TicketDTO ticketDTO);

    @Named("idToSession")
    protected Session idToSession(Long id) {
        if (id == null) return null;
        Session session = new Session();
        session.setId(id);
        return session;
    }

    @Named("idToUser")
    protected User idToUser(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }
}
