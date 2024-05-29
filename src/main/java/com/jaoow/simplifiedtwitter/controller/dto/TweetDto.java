package com.jaoow.simplifiedtwitter.controller.dto;

public record TweetDto(long id, String content, String author, long createdAt) {
}
