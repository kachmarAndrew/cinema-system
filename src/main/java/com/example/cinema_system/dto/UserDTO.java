package com.example.cinema_system.dto;

import com.example.cinema_system.entity.enums.Role;
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
public class UserDTO implements DataTransferObject {

    private Long id;
    private String email;
    private String password;
    private String role;
    private BigDecimal balance;

}
