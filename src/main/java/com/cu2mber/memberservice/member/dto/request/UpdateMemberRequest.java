package com.cu2mber.memberservice.member.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 회원 정보 수정 요청 정보를 담는 DTO입니다.
 * <p>
 * 이름, 비밀번호, 휴대폰 번호 중
 * 변경이 필요한 값만 선택적으로 전달받습니다.
 * </p>
 *
 * <p>
 * 비밀번호 변경 시 {@code newPassword}와 {@code confirmPassword}는
 * 반드시 함께 전달되어야 합니다.
 * </p>
 *
 * @param memberName      수정할 회원 이름
 * @param newPassword     변경할 비밀번호
 * @param confirmPassword 변경 비밀번호 확인 값
 * @param memberPhone     수정할 휴대폰 번호
 */
public record UpdateMemberRequest(

        /**
         * 회원 이름
         * <p>
         * 2자 이상 20자 이하로 입력해야 합니다.
         * </p>
         */
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
        String memberName,

        /**
         * 변경할 비밀번호
         * <p>
         * 8~20자 길이이며,
         * 영문, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.
         * </p>
         */
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*]).{8,20}$",
                message = "비밀번호는 8~20자, 영문/숫자/특수문자 중 2가지 이상을 조합해야 합니다."
        )
        String newPassword,

        /**
         * 변경 비밀번호 확인 값
         * <p>
         * {@code newPassword}와 일치 여부를 검증하기 위해 사용됩니다.
         * </p>
         */
        String confirmPassword,

        /**
         * 회원 휴대폰 번호
         * <p>
         * '-'를 제외한 숫자만 입력하며,
         * 01012345678 형식으로 검증합니다.
         * </p>
         */
        @Pattern(
                regexp = "^01[016789]\\d{7,8}$",
                message = "휴대폰 번호는 01012345678 형식으로 입력해주세요."
        )
        String memberPhone
) {
}