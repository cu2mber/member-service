package com.cu2mber.memberservice.common.exception;

/**
 * HTTP 401 Unauthorized 상황을 표현하는 커스텀 예외 클래스입니다.
 * <p>
 * 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 발생하며,
 * 로그인되지 않았거나 유효하지 않은 인증 정보가 제공된 경우에 사용됩니다.
 * </p>
 */
public class UnauthorizedException extends CommonHttpException {

    /**
     * Unauthorized에 해당하는 HTTP 상태 코드입니다.
     */
    private static final int STATUS_CODE = 401;

    /**
     * 사용자 정의 메시지를 포함하는 UnauthorizedException을 생성합니다.
     *
     * @param message 예외와 함께 전달할 상세 메시지
     */
    public UnauthorizedException(String message) {
        super(STATUS_CODE, message);
    }
}
