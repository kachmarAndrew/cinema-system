package com.example.cinema_system.service.implementation;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.entity.User;
import com.example.cinema_system.entity.enums.Role;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.logger.Logger;
import com.example.cinema_system.mapper.UserMapper;
import com.example.cinema_system.repository.UserRepository;
import com.example.cinema_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final Logger securityLogger;

    @Override
    @Transactional
    public UserDTO register(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        securityLogger.logRegistrationSuccess(userDTO.getEmail());
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(String email) {
        if (email == null) {
            throw new UserNotFoundException("user.not_found");
        }
        userRepository.deleteByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository
                .findUserByEmail(email)
                .map(userMapper::toUserDTO);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository
                .findUserById(id)
                .map(userMapper::toUserDTO);
    }

    @Override
    @Transactional
    public void changePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user.not_found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        securityLogger.logPasswordResetSuccess(email);
    }

    @Override
    public void addToBalance(String email, BigDecimal amount) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user.not_found"));
        user.setBalance(user.getBalance().add(amount));
    }

    @Override
    public void decreaseBalance(String email, BigDecimal amount) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user.not_found"));
        user.setBalance(user.getBalance().subtract(amount));
    }

    @Override
    public void changeUserRole(String email, Role role) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user.not_found"));

        user.setRole(role);
        securityLogger.logRoleSuccessfullyChanged(email, role);
    }

    @Override
    public List<UserDTO> getUsersByRole(Role role) {
        return userRepository
                .findUsersByRole(role).stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

}
