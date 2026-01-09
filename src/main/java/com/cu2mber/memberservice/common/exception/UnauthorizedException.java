package com.cu2mber.memberservice.common.exception;

public class UnauthorizedException extends CommonHttpException {
    private static final int STATUS_CODE = 401;

    public UnauthorizedException(String message) {
        super(STATUS_CODE, message);
    }
}
