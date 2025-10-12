package com.example.cinema_system.dto;

import com.example.cinema_system.util.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO implements DataTransferObject {

    private Long id;
    private String userName;
    private Long filmId;
    private String description;

}
