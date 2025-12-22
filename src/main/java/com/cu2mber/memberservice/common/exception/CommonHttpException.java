package com.cu2mber.memberservice.common.exception;

import lombok.Getter;

@Getter
public class CommonHttpException extends RuntimeException {

    private final int statusCode;

    public CommonHttpException(final int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
