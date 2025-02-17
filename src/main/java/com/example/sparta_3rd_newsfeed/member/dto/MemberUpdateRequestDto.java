package com.example.sparta_3rd_newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequestDto {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String oldPassword;  // 현재 비밀번호

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,16}$",
            message = "새 비밀번호는 8~16자, 영문자, 숫자, 특수기호를 각각 하나 이상 포함해야 합니다.")
    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;  // 새 비밀번호

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,16}$",
            message = "새 비밀번호는 8~16자, 영문자, 숫자, 특수기호를 각각 하나 이상 포함해야 합니다.")
    @NotBlank(message = "새 비밀번호를 확인해주세요.")
    private boolean passwordcheck;  // 새 비밀번호 확인

    private String profileImg;  // 프로필 이미지 URL
    @Size(max = 30, message = "소개글은 30자 이내로 작성해주세요.")
    private String profileBio;  // 소개글
}