package com.example.sparta_3rd_newsfeed.member.service;

import com.example.sparta_3rd_newsfeed.common.encoder.PasswordEncoder;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.MemberUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.requestDto.SignUpRequestDto;
import com.example.sparta_3rd_newsfeed.member.dto.responseDto.SignUpResponseDto;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //단건조회를 위한 메서드 추가
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."));
    }

    public List<Member> getMembersByUsername(String username) {
        // username을 포함한 회원들을 조회
        List<Member> members = memberRepository.findByUsernameContaining(username);

        if (members.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다.");
        }

        return members;
    }

    //회원수정
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
        if (!updateRequestDto.getNewPassword().equals(updateRequestDto.getPasswordcheck())) {
            throw new IllegalArgumentException("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        }

        // 비밀번호 변경
        String encodedPassword = passwordEncoder.encode(updateRequestDto.getNewPassword());
        member.setPassword(encodedPassword);

        // 4. 소개글 수정
        if (updateRequestDto.getProfileBio() != null) {
            member.setProfileBio(updateRequestDto.getProfileBio());
        }

        // 5. 프로필 이미지 수정
        if (updateRequestDto.getProfileImg() != null) {
            member.setProfileImg(updateRequestDto.getProfileImg());
        }

        // 6. 수정된 회원 저장
        return memberRepository.save(member);
    }


    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        // 활성화된 계정이 있는지 확인
        Optional<Member> existingMember = memberRepository.findByEmail(signUpRequestDto.getEmail());

        if (existingMember.isPresent()) {
            Member member = existingMember.get();

            if (member.isDeleted() && member.getDeletedAt() != null) {
                LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

                // 탈퇴한 계정이지만 탈퇴한 지 1달이 지나지 않은 계정일 경우
                if (member.getDeletedAt().isAfter(oneMonthAgo) &&
                        member.getPhoneNumber().equals(signUpRequestDto.getPhoneNumber())) {

                    member.setDeleted(false);
                    member.setDeletedAt(null);
                    member.setUsername(signUpRequestDto.getUsername());
                    member.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

                    if (signUpRequestDto.getProfileImg() != null) {
                        member.setProfileImg(signUpRequestDto.getProfileImg());
                    }
                    if (signUpRequestDto.getProfileBio() != null) {
                        member.setProfileBio(signUpRequestDto.getProfileBio());
                    }

                    memberRepository.save(member);
                    return new SignUpResponseDto(member.getUsername(), member.getEmail());
                } else {
                    // 전화번호가 다르거나, 1달이 지났다면 기존 계정 삭제 후 새 계정 생성
                    memberRepository.delete(member);
                }
            } else {
                // 기존 계정이 삭제된 상태가 아니라면 중복 이메일 예외 발생
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
            }
        }

        // 신규 회원가입 처리
        Member member = new Member();
        member.setUsername(signUpRequestDto.getUsername());
        member.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        member.setEmail(signUpRequestDto.getEmail());
        member.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        member.setProfileImg(signUpRequestDto.getProfileImg());
        member.setProfileBio(signUpRequestDto.getProfileBio());

        memberRepository.save(member);

        return new SignUpResponseDto(member.getUsername(), member.getEmail());
    }


    public void deleteMember(Member loggedInMember) {
        // 회원 정보 조회
        Member member = memberRepository.findByEmail(loggedInMember.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."));

        // 이미 탈퇴한 계정인지 확인
        if (member.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 계정입니다.");
        }

        // 탈퇴 처리 (isDeleted 필드 변경)
        member.setDeleted(true);
        member.setDeletedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

}