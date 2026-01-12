package com.cu2mber.memberservice.common.exception;

/**
 * HTTP 403 Forbidden 상황을 표현하는 커스텀 예외 클래스입니다.
 * <p>
 * 인증은 완료되었으나,
 * 해당 리소스에 접근할 권한이 없는 경우 사용됩니다.
 * 주로 인가(Authorization) 실패 상황에서 활용됩니다.
 * </p>
 */
public class ForbiddenException extends CommonHttpException {

    /**
     * Forbidden에 해당하는 HTTP 상태 코드입니다.
     */
    private static final int STATUS_CODE = 403;

    /**
     * 사용자 정의 메시지를 포함하는 ForbiddenException을 생성합니다.
     *
     * @param message 예외와 함께 전달할 상세 메시지
     */
    public ForbiddenException(String message) {
        super(STATUS_CODE, message);
    }

    /**
     * 기본 메시지를 사용하는 ForbiddenException을 생성합니다.
     * <p>
     * 메시지는 {@code "FORBIDDEN EXCEPTION"}으로 설정됩니다.
     * </p>
     */
    public ForbiddenException() {
        super(STATUS_CODE, "FORBIDDEN EXCEPTION");
    }
}