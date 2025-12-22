package com.cu2mber.memberservice.member.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GOV("ROLE_GOV");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
