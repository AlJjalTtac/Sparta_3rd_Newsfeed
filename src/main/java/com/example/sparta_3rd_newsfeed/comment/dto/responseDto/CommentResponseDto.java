package com.example.sparta_3rd_newsfeed.comment.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final String contents;
}
