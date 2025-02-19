package com.example.sparta_3rd_newsfeed.feed.dto;

import com.example.sparta_3rd_newsfeed.comment.dto.CommentSimpleResponseDto;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FeedDetailDto {
    private final Long id;
    private final String title;
    private final String content;
    private final long likeCount;
    private final List<CommentSimpleResponseDto> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedDetailDto(Feed feed, long likeCount, List<CommentSimpleResponseDto> comments) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.likeCount = likeCount;
        this.comments = comments;
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
    }
}
