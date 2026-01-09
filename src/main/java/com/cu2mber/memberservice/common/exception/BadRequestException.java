package com.cu2mber.memberservice.common.exception;

/**
 * HTTP 400 Bad Request 상황을 표현하는 커스텀 예외 클래스입니다.
 * <p>
 * 클라이언트 요청이 잘못되었거나
 * 유효하지 않은 입력값이 전달되었을 때 사용됩니다.
 * </p>
 */
public class BadRequestException extends CommonHttpException {

    /**
     * Bad Request에 해당하는 HTTP 상태 코드입니다.
     */
    private static final int STATUS_CODE = 400;

    /**
     * 사용자 정의 메시지를 포함하는 BadRequestException을 생성합니다.
     *
     * @param message 예외와 함께 전달할 상세 메시지
     */
    public BadRequestException(String message) {
        super(STATUS_CODE, message);
    }

    /**
     * 기본 메시지를 사용하는 BadRequestException을 생성합니다.
     * <p>
     * 메시지는 {@code "Bad Request"}로 설정됩니다.
     * </p>
     */
    public BadRequestException() {
        super(STATUS_CODE, "Bad Request");
    }
}
