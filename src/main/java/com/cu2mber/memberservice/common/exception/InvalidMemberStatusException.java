package com.cu2mber.memberservice.common.exception;

public class InvalidMemberStatusException extends RuntimeException {
    public InvalidMemberStatusException(String message) {
        super(message);
    }
}
