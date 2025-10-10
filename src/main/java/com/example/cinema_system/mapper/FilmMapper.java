package com.example.cinema_system.mapper;

import com.example.cinema_system.dto.FilmDTO;
import com.example.cinema_system.entity.Film;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { SessionMapper.class, EnumMapper.class })
public abstract class FilmMapper {

    protected final SessionMapper sessionMapper;
    protected final EnumMapper enumMapper;

    public FilmMapper(SessionMapper sessionMapper, EnumMapper enumMapper) {
        this.sessionMapper = sessionMapper;
        this.enumMapper = enumMapper;
    }

    @Mapping(target = "sessions", source = "sessions") // MapStruct автоматично викличе SessionMapper
    @Mapping(target = "genre", expression = "java(enumMapper.genreToString(film.getGenre()))")
    public abstract FilmDTO toFilmDTO(Film film);

    @Mapping(target = "sessions", ignore = true) // sessions створюємо окремо
    @Mapping(target = "genre", expression = "java(enumMapper.stringToGenre(filmDTO.getGenre()))")
    public abstract Film toFilm(FilmDTO filmDTO);
}

