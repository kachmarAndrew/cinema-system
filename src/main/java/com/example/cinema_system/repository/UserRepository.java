package com.example.cinema_system.repository;

import com.example.cinema_system.model.User;
import com.example.cinema_system.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    void deleteByEmail(String email);
    List<User> findUsersByRole(Role role);
}
