package com.jaoow.simplifiedtwitter.controller.dto;

public record LoginResponse(String token, long expiresIn) {
}
