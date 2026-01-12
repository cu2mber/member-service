package com.cu2mber.memberservice.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 지자체(GOV) 회원 가입 요청 정보를 담는 DTO입니다.
 * <p>
 * 지자체 계정 생성을 위한 기본 정보와
 * 비밀번호 검증을 위한 확인 비밀번호를 포함합니다.
 * </p>
 *
 * @param memberEmail     지자체 회원 이메일
 * @param memberName      지자체 담당자 이름
 * @param memberPwd       비밀번호
 * @param confirmPassword 비밀번호 확인 값
 * @param memberPhone     지자체 연락처
 */
public record SignUpGovRequest(

        /**
         * 지자체 회원 이메일
         * <p>
         * 이메일 형식 검증을 수행하며,
         * 로그인 ID로 사용됩니다.
         * </p>
         */
        @NotBlank(message = "지자체 이메일을 아이디로 사용하세요.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "유효한 이메일 주소를 입력해주세요."
        )
        String memberEmail,

        /**
         * 지자체 담당자 이름
         */
        @NotBlank
        String memberName,

        /**
         * 비밀번호
         * <p>
         * 8~20자 길이이며,
         * 영문, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.
         * </p>
         */
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*]).{8,20}$",
                message = "비밀번호는 8~20자, 영문/숫자/특수문자 중 2가지 이상을 조합해야 합니다."
        )
        String memberPwd,

        /**
         * 비밀번호 확인 값
         * <p>
         * 비밀번호와 일치 여부를 검증하기 위해 사용됩니다.
         * </p>
         */
        @NotBlank(message = "비밀번호를 다시 입력해주세요.")
        String confirmPassword,

        /**
         * 지자체 연락처
         * <p>
         * 숫자만 입력 가능하며,
         * 9~11자리 형식을 검증합니다.
         * </p>
         */
        @NotBlank(message = "지자체 전화번호를 입력해주세요.")
        @Pattern(
                regexp = "^\\d{9,11}$",
                message = "유효한 지자체 전화번호를 입력해주세요."
        )
        String memberPhone
) {
}
