package com.example.sparta_3rd_newsfeed.member.service;

import com.example.sparta_3rd_newsfeed.member.dto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member updateMember(Long memberId, MemberUpdateRequestDto updateRequestDto) {
        // 1. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        // 2. 현재 비밀번호 확인
        if (!passwordEncoder.matches(updateRequestDto.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 3. 새 비밀번호 형식 검증 및 변경
        // 3. 새 비밀번호 형식 검증 및 변경
        if (updateRequestDto.isPasswordcheck()) {  // passwordcheck가 true일 때 새 비밀번호 확인
            if (!updateRequestDto.getNewPassword().equals(updateRequestDto.getNewPassword())) {
                throw new IllegalArgumentException("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
            }


        // 비밀번호 형식 검증 (8~16자, 영문자, 숫자, 특수문자 각 1개 이상)
            String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(updateRequestDto.getNewPassword());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("새 비밀번호는 8~16자, 영문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.");
            }

            // 비밀번호 변경
            String encodedPassword = passwordEncoder.encode(updateRequestDto.getNewPassword());
            member.setPassword(encodedPassword);
        }

        // 4. 소개글 수정
        if (updateRequestDto.getProfileBio() != null) {
            if (updateRequestDto.getProfileBio().length() > 30) {
                throw new IllegalArgumentException("소개글은 30자 이하로 작성해주세요.");
            }
            member.setProfileBio(updateRequestDto.getProfileBio());
        }

        // 5. 프로필 이미지 수정
        if (updateRequestDto.getProfileImg() != null) {
            member.setProfileImgId(updateRequestDto.getProfileImg());
        }

        // 6. 수정된 회원 저장
        return memberRepository.save(member);
    }

}