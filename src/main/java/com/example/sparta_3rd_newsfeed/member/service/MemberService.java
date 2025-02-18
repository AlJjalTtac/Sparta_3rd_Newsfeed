package com.example.sparta_3rd_newsfeed.member.service;

import com.example.sparta_3rd_newsfeed.common.encoder.PasswordEncoder;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.DeleteMemberRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.LoginRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.SignUpRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.SignUpResponseDto;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        // 활성화된 계정이 있는지 확인
        Optional<Member> existingMember = memberRepository.findByEmailAndIsDeletedFalse(signUpRequestDto.getEmail());

        if (existingMember.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다.");
        }

        // 비밀번호 유효성 검사
        if (!signUpRequestDto.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        }

        // 기존 탈퇴한 회원이 있는지 확인
        Optional<Member> deletedMember = memberRepository.findByEmail(signUpRequestDto.getEmail());

        if (deletedMember.isPresent()) {
            // 기존 계정을 재활성화
            Member member = deletedMember.get();
            member.setDeleted(false); // 계정 활성화
            member.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword())); // 비밀번호 재설정
            member.setUsername(signUpRequestDto.getUsername());
            member.setProfileImg(signUpRequestDto.getProfileImg());
            member.setProfileBio(signUpRequestDto.getProfileBio());
            memberRepository.save(member);

            return new SignUpResponseDto(member.getUsername(), member.getEmail());
        }

        // 신규 회원가입 처리
        Member member = new Member();
        member.setUsername(signUpRequestDto.getUsername());
        member.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        member.setEmail(signUpRequestDto.getEmail());
        member.setProfileImg(signUpRequestDto.getProfileImg());
        member.setProfileBio(signUpRequestDto.getProfileBio());

        memberRepository.save(member);

        return new SignUpResponseDto(member.getUsername(), member.getEmail());
    }


//    public Member login(LoginRequestDto loginRequestDto) {
//        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다."));
//
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다.");
//        }
//
//        return member;
//    }


    public void deleteMember(DeleteMemberRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"이메일이나 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다.");
        }

        member.setDeleted(true);
        memberRepository.save(member);
    }
}
