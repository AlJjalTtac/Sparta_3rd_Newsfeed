package com.example.sparta_3rd_newsfeed.feed.dto;

import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
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

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.memberEmail = feed.getMember().getEmail();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
    }


}
