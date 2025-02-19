package com.example.sparta_3rd_newsfeed.member.controller;

import com.example.sparta_3rd_newsfeed.member.dto.LoginRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.LoginResponseDto;
/*import com.example.sparta_3rd_newsfeed.member.dto.LoginResponseDto;
import com.example.sparta_3rd_newsfeed.member.dto.LoginRequestDto;*/
import com.example.sparta_3rd_newsfeed.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// 로그인 컨트롤러
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletRequest request) {
        return ResponseEntity.ok(loginService.login(requestDto, request));
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponseDto> logout(HttpServletRequest request) {
        return ResponseEntity.ok(loginService.logout(request));
    }
}