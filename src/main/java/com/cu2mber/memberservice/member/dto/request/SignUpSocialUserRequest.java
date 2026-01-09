package com.cu2mber.memberservice.member.dto.request;

import com.cu2mber.memberservice.member.enums.AuthProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 소셜 로그인 기반 회원 가입 요청 정보를 담는 DTO입니다.
 * <p>
 * 최초 소셜 로그인 시 추가 정보 입력을 통해
 * 회원 가입을 완료하기 위해 사용됩니다.
 * </p>
 *
 * @param memberEmail 회원 이메일
 * @param memberName  회원 이름
 * @param memberPhone 회원 휴대폰 번호
 * @param memberBirth 회원 생년월일 (YYMMDD)
 * @param provider    소셜 인증 제공자
 * @param providerId  소셜 제공자에서 발급한 사용자 식별자
 */
public record SignUpSocialUserRequest(

        /**
         * 회원 이메일
         * <p>
         * 로그인 ID로 사용되며,
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
         * 회원 이름
         * <p>
         * 2자 이상 20자 이하로 입력해야 합니다.
         * </p>
         */
        @NotBlank(message = "본인의 이름을 정자로 입력해주세요.")
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
        String memberName,

        /**
         * 회원 휴대폰 번호
         * <p>
         * '-'를 제외한 숫자만 입력하며,
         * 01012345678 형식으로 검증합니다.
         * </p>
         */
        @NotBlank(message = "- 를 제외한 휴대폰번호를 입력해주세요.")
        @Pattern(
                regexp = "^01[016789]\\d{7,8}$",
                message = "휴대폰 번호는 01012345678 형식으로 입력해주세요."
        )
        String memberPhone,

        /**
         * 회원 생년월일
         * <p>
         * YYMMDD 형식의 문자열로 입력받으며,
         * 내부 로직에서 날짜 타입으로 변환됩니다.
         * </p>
         */
        @NotBlank(message = "생년월일 6자리를 입력해주세요.(예시: 991231")
        @Pattern(
                regexp = "^[0-9]{6}",
                message = "생년월일은 991231 형식으로 입력해주세요."
        )
        String memberBirth,

        /**
         * 소셜 인증 제공자
         * <p>
         * KAKAO, GOOGLE 등의 인증 제공자를 의미합니다.
         * </p>
         */
        @NotNull
        AuthProvider provider,

        /**
         * 소셜 인증 제공자에서 발급한 사용자 고유 식별자
         */
        @NotBlank
        String providerId
) {
}
