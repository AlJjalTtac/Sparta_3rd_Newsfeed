package com.example.sparta_3rd_newsfeed.member.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String email;

    private final String password;

    public LoginResponseDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
