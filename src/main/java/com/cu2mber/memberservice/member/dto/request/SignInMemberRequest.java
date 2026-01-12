package com.cu2mber.memberservice.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 회원 로그인 요청 정보를 담는 DTO입니다.
 * <p>
 * 이메일과 비밀번호를 입력받아
 * 회원 인증을 수행하는 데 사용됩니다.
 * </p>
 *
 * @param memberEmail 회원 이메일
 * @param memberPwd   회원 비밀번호
 */
public record SignInMemberRequest (

        /**
         * 회원 이메일
         * <p>
         * 이메일 형식 검증을 수행합니다.
         * </p>
         */
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "유효한 이메일 주소를 입력해주세요."
        )
        String memberEmail,

        /**
         * 회원 비밀번호
         * <p>
         * 공백일 수 없으며,
         * 인증 과정에서 사용됩니다.
         * </p>
         */
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String memberPwd
){
}
