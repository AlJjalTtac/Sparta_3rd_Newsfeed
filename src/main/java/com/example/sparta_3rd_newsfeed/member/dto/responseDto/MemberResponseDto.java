package com.example.sparta_3rd_newsfeed.member.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private String username;
    private String email;
    private String profileImg;
    private String profileBio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

