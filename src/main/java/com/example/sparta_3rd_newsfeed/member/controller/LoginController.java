package com.example.sparta_3rd_newsfeed.member.controller;

import com.example.sparta_3rd_newsfeed.member.dto.LoginRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.LoginResponseDto;
import com.example.sparta_3rd_newsfeed.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody  LoginRequestDto requestDto, HttpServletRequest request) {
        LoginResponseDto responseDto = loginService.login(requestDto.getEmail(), requestDto.getPassword(), request);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponseDto> logout(@RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        LoginResponseDto responseDto = LoginService.logout(request);
        return ResponseEntity.ok(responseDto);
    }
}
