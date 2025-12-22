package com.cu2mber.memberservice.member.enums;

import lombok.Getter;

@Getter
public enum AuthProvider {
    LOCAL("일반 가입"),
    GOOGLE("구글"),
    KAKAO("카카오");

    private final String auth;

    AuthProvider(String auth) {
        this.auth = auth;
    }
}
