package com.example.sparta_3rd_newsfeed.member.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberRequestDto {
    private String email;
    private String password;
}
