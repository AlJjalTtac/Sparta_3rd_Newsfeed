package com.example.sparta_3rd_newsfeed.feed.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String memberEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


}
