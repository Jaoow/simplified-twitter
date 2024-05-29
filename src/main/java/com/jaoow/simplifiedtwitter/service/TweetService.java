package com.jaoow.simplifiedtwitter.service;

import com.jaoow.simplifiedtwitter.controller.dto.CreateTweetDto;
import com.jaoow.simplifiedtwitter.controller.dto.TweetDto;
import com.jaoow.simplifiedtwitter.entity.Tweet;
import com.jaoow.simplifiedtwitter.repository.TweetRepository;
import com.jaoow.simplifiedtwitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Page<TweetDto> listTweets(int page, int pageSize) {
        return tweetRepository
                .findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet ->
                        new TweetDto(
                                tweet.getTweetId(),
                                tweet.getContent(),
                                tweet.getUser().getUsername(),
                                tweet.getCreationTimestamp().toEpochMilli()
                        )
                );
    }

    public void createTweet(CreateTweetDto tweetDto, String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var tweet = Tweet.builder()
                .content(tweetDto.content())
                .user(user)
                .build();

        tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId, String userId) {
        tweetRepository.findById(tweetId).ifPresent(tweet -> {

            boolean isNotOwnTweet = !tweet.getUser().getId().toString().equals(userId);
            boolean isAdmin = userService.isAdmin(UUID.fromString(userId));

            if (isNotOwnTweet && !isAdmin) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete this tweet");
            }

            tweetRepository.delete(tweet);
        });
    }
}
