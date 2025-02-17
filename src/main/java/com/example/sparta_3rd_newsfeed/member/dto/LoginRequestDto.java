package com.example.sparta_3rd_newsfeed.member.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private final String email;
    private final String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
