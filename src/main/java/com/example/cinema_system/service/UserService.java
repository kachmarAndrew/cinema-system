package com.example.cinema_system.service;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.entity.enums.Role;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    UserDTO register(UserDTO userDTO);
    void deleteUser(Long userId);

    Boolean existByEmail(String email);
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Long id);

    void changePassword(String email, String newPassword);
    void addToBalance(String email, BigDecimal amount);
    void decreaseBalance(String email, BigDecimal amount);
    void changeUserRole(Long userId, Role role);

    List<UserDTO> getUsersByRole(Role role);

}
