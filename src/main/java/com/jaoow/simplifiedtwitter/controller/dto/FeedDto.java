package com.jaoow.simplifiedtwitter.controller.dto;

import java.util.List;

public record FeedDto(List<TweetDto> tweets, int page, int pageSize, int totalPages, long totalElements) {
}
