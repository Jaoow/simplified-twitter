package com.jaoow.simplifiedtwitter.controller;

import com.jaoow.simplifiedtwitter.controller.dto.LoginRequest;
import com.jaoow.simplifiedtwitter.controller.dto.LoginResponse;
import com.jaoow.simplifiedtwitter.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        var jwt = tokenService.generateToken(loginRequest);
        return new LoginResponse(jwt, 300);
    }
}
