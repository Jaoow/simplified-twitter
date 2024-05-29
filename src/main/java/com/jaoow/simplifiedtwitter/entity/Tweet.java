package com.jaoow.simplifiedtwitter.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_tweets")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Tweet {

    @Id
    @Column(name = "tweet_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @CreationTimestamp
    private Instant creationTimestamp;

    public Tweet() {

    }
}
