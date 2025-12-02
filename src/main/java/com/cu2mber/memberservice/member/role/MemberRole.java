package com.cu2mber.memberservice.member.role;

public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GOV("ROLE_GOV");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
