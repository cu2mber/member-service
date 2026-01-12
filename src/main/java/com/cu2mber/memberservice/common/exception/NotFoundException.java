package com.cu2mber.memberservice.common.exception;

/**
 * HTTP 404 Not Found 상황을 표현하는 커스텀 예외 클래스입니다.
 * <p>
 * 요청한 리소스를 찾을 수 없을 때 사용되며,
 * 존재하지 않는 식별자(ID) 조회나
 * 이미 삭제된 리소스 접근 시 활용됩니다.
 * </p>
 */
public class NotFoundException extends CommonHttpException {

    /**
     * Not Found에 해당하는 HTTP 상태 코드입니다.
     */
    private static final int STATUS_CODE = 404;

    /**
     * 사용자 정의 메시지를 포함하는 NotFoundException을 생성합니다.
     *
     * @param message 예외와 함께 전달할 상세 메시지
     */
    public NotFoundException(String message) {
        super(STATUS_CODE, message);
    }

    /**
     * 기본 메시지를 사용하는 NotFoundException을 생성합니다.
     * <p>
     * 메시지는 {@code "NOT FOUND EXCEPTION"}으로 설정됩니다.
     * </p>
     */
    public NotFoundException() {
        super(STATUS_CODE, "NOT FOUND EXCEPTION");
    }
}
