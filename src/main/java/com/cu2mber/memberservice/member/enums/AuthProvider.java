package com.cu2mber.memberservice.member.enums;

import lombok.Getter;

/**
 * 회원 인증 제공자를 구분하기 위한 열거형입니다.
 * <p>
 * 일반 회원가입(Local) 및 소셜 로그인(Google, Kakao)을 구분하며,
 * 사용자에게 표시하기 위한 설명 문자열을 함께 제공합니다.
 * </p>
 */
@Getter
public enum AuthProvider {

    /** 일반 회원가입 */
    LOCAL("일반 가입"),

    /** 구글 소셜 로그인 */
    GOOGLE("구글"),

    /** 카카오 소셜 로그인 */
    KAKAO("카카오");

    /** 인증 제공자 설명 */
    private final String auth;

    AuthProvider(String auth) {
        this.auth = auth;
    }
}
