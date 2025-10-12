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
public class SessionDTO implements DataTransferObject {

    private Long id;
    private Long filmId;
    private Integer quantityOfSeats;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<TicketDTO> tickets;

}
