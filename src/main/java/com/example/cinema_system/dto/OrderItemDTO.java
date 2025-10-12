package com.example.cinema_system.dto;

import com.example.cinema_system.util.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO implements DataTransferObject {

    private Long id;
    private Long orderId;
    private Long ticketId;
    private BigDecimal price;

}
