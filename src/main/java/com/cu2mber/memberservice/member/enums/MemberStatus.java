package com.cu2mber.memberservice.member.enums;

public enum MemberStatus {

    ACTIVE("ACTIVE"),

    PENDING("PENDING"),

    LOCKED("LOCKED"),

    WITHDRAWN("WITHDRAWN");

    private final String status;

    MemberStatus(String status) {
        this.status = status;
    }
}
