package com.jaoow.simplifiedtwitter.controller;

import com.jaoow.simplifiedtwitter.controller.dto.CreateUserDto;
import com.jaoow.simplifiedtwitter.entity.User;
import com.jaoow.simplifiedtwitter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<User> listUsers() {
        return userService.listUsers();
    }
}
