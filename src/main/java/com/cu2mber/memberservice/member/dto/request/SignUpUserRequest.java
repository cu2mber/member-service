package com.cu2mber.memberservice.member.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpUserRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "유효한 이메일 주소를 입력해주세요."
        )
        String memberEmail,

        @NotBlank(message = "본인의 이름을 정자로 입력해주세요.")
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
        String memberName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*]).{8,20}$",
                message = "비밀번호는 8~20자, 영문/숫자/특수문자 중 2가지 이상을 조합해야 합니다."
        )
        String memberPwd,

        @NotBlank(message = "비밀번호를 다시 입력해주세요.")
        String confirmPassword,

        @NotBlank(message = "- 를 제외한 휴대폰번호를 입력해주세요.")
        @Pattern(
                regexp = "^01[016789]\\d{7,8}$",
                message = "휴대폰 번호는 01012345678 형식으로 입력해주세요."
        )
        String memberPhone,

        @NotBlank(message = "생년월일 6자리를 입력해주세요.(예시: 991231")
        @Pattern(
                regexp = "^[0-9]{6}$",
                message = "생년월일은 991231 형식으로 입력해주세요."
        )
        String memberBirth
) {
}