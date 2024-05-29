package com.jaoow.simplifiedtwitter.controller;

import com.jaoow.simplifiedtwitter.controller.dto.CreateTweetDto;
import com.jaoow.simplifiedtwitter.controller.dto.FeedDto;
import com.jaoow.simplifiedtwitter.controller.dto.TweetDto;
import com.jaoow.simplifiedtwitter.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping("/feed")
    public FeedDto feed(@RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<TweetDto> tweetDtos = tweetService.listTweets(page, pageSize);
        return new FeedDto(tweetDtos.getContent(), page, pageSize, tweetDtos.getTotalPages(), tweetDtos.getTotalElements());
    }

    @PostMapping
    public void createTweet(@RequestBody CreateTweetDto tweetDto, JwtAuthenticationToken token) {
        tweetService.createTweet(tweetDto, token.getToken().getSubject());
    }

    @DeleteMapping("/{tweetId}")
    public void deleteTweet(@PathVariable Long tweetId, JwtAuthenticationToken token) {
        tweetService.deleteTweet(tweetId, token.getToken().getSubject());
    }
}
