package com.example.cinema_system.dto;

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
public class SessionDTO {

    private Long id;
    private Long filmId;
    private Integer quantityOfSeats;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<TicketDTO> tickets;

}
