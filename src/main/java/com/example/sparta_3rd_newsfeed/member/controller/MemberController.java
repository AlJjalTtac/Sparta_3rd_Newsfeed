package com.example.sparta_3rd_newsfeed.member.controller;

import com.example.sparta_3rd_newsfeed.feed.entity.Like;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.DeleteMemberRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.LoginRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.SignUpRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.LoginResponseDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.MemberResponseDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.SignUpResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    // 단건 조회 - id 기준
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id); // id로 멤버 조회

        if (member == null) {
            return ResponseEntity.notFound().build(); // 멤버가 없으면 404 반환
        }

        MemberResponseDto responseDto = new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getProfileImg(),
                member.getProfileBio(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );

        return ResponseEntity.ok(responseDto);
    }

    //멤버 다건조회
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembersByUsername(@RequestParam String username) {
        List<Member> members = memberService.getMembersByUsername(username); // 다건 조회

        // List<Member>에서 List<MemberResponseDto>로 변환
        List<MemberResponseDto> responseDtos = members.stream()
                .map(member -> new MemberResponseDto(
                        member.getId(),
                        member.getUsername(),
                        member.getEmail(),
                        member.getProfileImg(),
                        member.getProfileBio(),
                        member.getCreatedAt(), // LocalDateTime 전달
                        member.getUpdatedAt()  // LocalDateTime 전달
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    // 내 정보 수정
    @PutMapping("/me")
    public ResponseEntity<Member> updateProfile(
            @SessionAttribute(name = "member") Member member,  // 로그인된 사용자
            @RequestBody @Valid MemberUpdateRequestDto updateRequestDto  // @Valid 추가
            ) {
        //유효성 검사 오류 발생 확인
        Member updatedMember = memberService.updateMember(member.getId(), updateRequestDto);
        return ResponseEntity.ok(updatedMember);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage()) // 필드명과 메시지 함께 반환
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        SignUpResponseDto signUpResponseDto = memberService.signUp(signUpRequestDto);
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMember(
            @RequestBody DeleteMemberRequestDto requestDto
    ) {

        memberService.deleteMember(requestDto);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}

