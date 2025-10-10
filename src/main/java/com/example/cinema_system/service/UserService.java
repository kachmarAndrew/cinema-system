package com.example.cinema_system.service;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.entity.enums.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO register(UserDTO userDTO);
    void deleteUser(String email);

    Boolean existsByEmail(String email);
    Optional<UserDTO> getUserByEmail(String email);
    Optional<UserDTO> getUserById(Long id);

    void changePassword(String email, String newPassword);
    void addToBalance(String email, BigDecimal amount);
    void decreaseBalance(String email, BigDecimal amount);
    void changeUserRole(String email, Role role);

    List<UserDTO> getUsersByRole(Role role);

}
