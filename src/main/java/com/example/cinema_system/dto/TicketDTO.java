package com.example.cinema_system.dto;

import com.example.cinema_system.util.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO implements DataTransferObject {

    private Long id;
    private Long sessionId;
    private Long userId;
    private Integer hallNumber;
    private Integer rowNumber;
    private Integer seatNumber;
    private String status;
    private BigDecimal price;
    private LocalDateTime createdAt;

}
