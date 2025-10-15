package com.example.cinema_system.dto;

import com.example.cinema_system.util.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmDTO implements DataTransferObject {

    private Long id;
    private String name;
    private String genre;
    private String language;
    private LocalDateTime releaseAt;
    private LocalDateTime endAt;
    private List<SessionDTO> sessions;
    private List<ReviewDTO> reviews;

}
