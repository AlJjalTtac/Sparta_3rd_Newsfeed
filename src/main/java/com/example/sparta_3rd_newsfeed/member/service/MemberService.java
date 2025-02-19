package com.example.sparta_3rd_newsfeed.member.service;

import com.example.sparta_3rd_newsfeed.common.encoder.PasswordEncoder;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.DeleteMemberRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.LoginRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.SignUpRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.SignUpResponseDto;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다.");
        }

        Member member = new Member();
        member.setUsername(requestDto.getUsername());
        member.setEmail(requestDto.getEmail());
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        member.setProfileImg(requestDto.getProfileImg());
        member.setProfileBio(requestDto.getProfileBio());

        memberRepository.save(member);
        return new SignUpResponseDto(member.getUsername(), member.getEmail());
    }
}
