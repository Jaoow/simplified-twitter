package com.jaoow.simplifiedtwitter.repository;

import com.jaoow.simplifiedtwitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
