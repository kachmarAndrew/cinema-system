package com.example.cinema_system.dto;

import com.example.cinema_system.util.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO implements DataTransferObject {

    private Long id;
    private Long userId;
    private BigDecimal total;
    private LocalDateTime paidAt;
    private List<OrderItemDTO> items;

}
