package com.example.sparta_3rd_newsfeed.feed.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedPageResponseDto {
    private final Long id;
    private final String title;
    private final String memberEmail;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedPageResponseDto(Long id, String title, String memberEmail, int commentCount,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.memberEmail = memberEmail;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
