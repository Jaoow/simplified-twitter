package com.jaoow.simplifiedtwitter.service;

import com.jaoow.simplifiedtwitter.controller.dto.LoginRequest;
import com.jaoow.simplifiedtwitter.entity.Role;
import com.jaoow.simplifiedtwitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String generateToken(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        Instant expiresAt = Instant.now().plus(300, ChronoUnit.SECONDS);

        var roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("scope", roles)
                .issuedAt(Instant.now())
                .expiresAt(expiresAt)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
