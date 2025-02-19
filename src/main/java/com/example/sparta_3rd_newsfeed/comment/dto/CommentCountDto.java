package com.example.sparta_3rd_newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentCountDto {

    private final Long feedId;
    private final Long count;

    public CommentCountDto (Long feedId, Long count) {
        this.feedId = feedId;
        this.count = count;
    }
}
