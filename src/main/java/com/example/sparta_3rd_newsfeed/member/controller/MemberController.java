package com.example.sparta_3rd_newsfeed.member.controller;

import com.example.sparta_3rd_newsfeed.member.dto.requestDto.DeleteMemberRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.SignUpRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.SignUpResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
// 회원 컨트롤러
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signUp(requestDto));
    }
}
