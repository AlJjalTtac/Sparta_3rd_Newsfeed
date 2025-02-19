package com.example.sparta_3rd_newsfeed.comment.dto.requestDto;

import com.example.sparta_3rd_newsfeed.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    private String contents;
}
