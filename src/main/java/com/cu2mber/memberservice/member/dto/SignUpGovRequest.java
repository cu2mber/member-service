package com.cu2mber.memberservice.member.dto;

import com.cu2mber.memberservice.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpGovRequest(
        @NotBlank(message = "지자체 이메일을 아이디로 사용하세요.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "유효한 이메일 주소를 입력해주세요."
        )
        String memberEmail,

        @NotBlank
        String memberName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=(?:.*[A-Za-z].*){0,})(?=(?:.*\\d.*){0,})(?=(?:.*[!@#$%^&*].*){0,}).{8,20}$" +
                        "(?:(?=.*[A-Za-z].*)(?=.*\\d.*)|(?=.*[A-Za-z].*)(?=.*[!@#$%^&*].*)|(?=.*\\d.*)(?=.*[!@#$%^&*].*))",
                message = "비밀번호는 8~20자, 영문/숫자/특수문자 중 2가지 이상을 조합해야 합니다."
        )
        String memberPwd,

        @NotBlank(message = "지자체 전화번호를 입력해주세요.")
        @Pattern(
                regexp = "^\\d{9,11}$",
                message = "유효한 지자체 전화번호를 입력해주세요."
        )
        String memberPhone
) {
}
