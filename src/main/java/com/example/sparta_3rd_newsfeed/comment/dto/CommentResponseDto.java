package com.example.sparta_3rd_newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String contents;

    public CommentResponseDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}
