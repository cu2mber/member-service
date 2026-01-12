package com.cu2mber.memberservice.common.exception;

import lombok.Getter;

/**
 * HTTP 상태 코드를 포함하는 공통 커스텀 예외의 최상위 클래스입니다.
 * <p>
 * 비즈니스 로직에서 발생하는 예외를
 * HTTP 응답 상태 코드와 함께 전달하기 위해 사용됩니다.
 * </p>
 * <p>
 * 해당 예외는 {@code @RestControllerAdvice}에서 공통적으로 처리되어
 * 상태 코드와 메시지가 클라이언트로 반환됩니다.
 * </p>
 */
@Getter
public class CommonHttpException extends RuntimeException {

    /**
     * HTTP 응답에 사용될 상태 코드입니다.
     */
    private final int statusCode;

    /**
     * 상태 코드와 메시지를 포함하는 CommonHttpException을 생성합니다.
     *
     * @param statusCode HTTP 응답 상태 코드
     * @param message    클라이언트에 전달할 예외 메시지
     */
    public CommonHttpException(final int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}