package com.example.cinema_system.mapper;

import com.example.cinema_system.util.DataTransferObject;

public interface ClassMapper<E, DTO extends DataTransferObject> {

    E toEntity(DTO dto);
    DTO toDTO(E entity);

}
