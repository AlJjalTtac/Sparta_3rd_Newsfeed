package com.example.sparta_3rd_newsfeed.feed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FeedLikeCountDto {
    private final Long id;
    private final String title;
    private final String content;
    private final long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedLikeCountDto(Feed feed, long likeCount) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.likeCount = likeCount;
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
    }
}
