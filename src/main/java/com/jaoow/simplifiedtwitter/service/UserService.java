package com.jaoow.simplifiedtwitter.service;

import com.jaoow.simplifiedtwitter.controller.dto.CreateUserDto;
import com.jaoow.simplifiedtwitter.entity.User;
import com.jaoow.simplifiedtwitter.repository.RoleRepository;
import com.jaoow.simplifiedtwitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(CreateUserDto userDto) {
        var basicRole = roleRepository.findByName("BASIC")
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        var userExists = userRepository.existsByUsername(userDto.username());
        if (userExists) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User already exists");
        }

        var user = User.builder()
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .roles(Set.of(basicRole))
                .build();

        userRepository.save(user);
    }

    public boolean isAdmin(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
