package com.example.sparta_3rd_newsfeed.member.controller;

import com.example.sparta_3rd_newsfeed.member.dto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 내 정보 수정
    @PutMapping("/me")
    public ResponseEntity<Member> updateProfile(
            @AuthenticationPrincipal Long loginUserId,  // 로그인된 사용자의 ID
            @RequestBody @Valid MemberUpdateRequestDto updateRequestDto,  // @Valid 추가
            BindingResult bindingResult) {  // 유효성 검사 결과
        //유효성 검사 오류 발생 확인
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null); //잘못된 요청시 400상태코드 반환
        }

        Member updatedMember = memberService.updateMember(loginUserId, updateRequestDto);
        return ResponseEntity.ok(updatedMember);
    }
}

