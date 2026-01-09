package com.cu2mber.memberservice.member.enums;

import lombok.Getter;

/**
 * 회원의 권한(Role)을 정의하는 열거형입니다.
 * <p>
 * Spring Security에서 사용하는 ROLE_ 접두사를 포함한 권한 문자열을 관리하며,
 * 인증 및 인가 처리 시 사용됩니다.
 * </p>
 */
@Getter
public enum MemberRole {

    /** 시스템 관리자 권한 */
    ADMIN("ROLE_ADMIN"),

    /** 일반 사용자 권한 */
    USER("ROLE_USER"),

    /** 지자체(기관) 사용자 권한 */
    GOV("ROLE_GOV");

    /** Spring Security 인가 처리를 위한 권한 문자열 */
    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
