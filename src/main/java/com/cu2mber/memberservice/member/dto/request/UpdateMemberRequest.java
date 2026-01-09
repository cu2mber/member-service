package com.cu2mber.memberservice.member.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMemberRequest(

        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
        String memberName,

        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*]).{8,20}$",
                message = "비밀번호는 8~20자, 영문/숫자/특수문자 중 2가지 이상을 조합해야 합니다."
        )
        String newPassword,

        String confirmPassword,

        @Pattern(
                regexp = "^01[016789]\\d{7,8}$",
                message = "휴대폰 번호는 01012345678 형식으로 입력해주세요."
        )
        String memberPhone
) {
}