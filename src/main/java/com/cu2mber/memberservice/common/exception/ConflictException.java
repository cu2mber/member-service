package com.cu2mber.memberservice.common.exception;

/**
 * HTTP 409 Conflict 상황을 표현하는 커스텀 예외 클래스입니다.
 * <p>
 * 이미 존재하는 리소스와의 충돌이 발생했을 때 사용되며,
 * 주로 중복된 데이터 생성 요청이나
 * 상태 충돌이 발생하는 경우에 활용됩니다.
 * </p>
 */
public class ConflictException extends CommonHttpException{

    /**
     * Conflict에 해당하는 HTTP 상태 코드입니다.
     */
    private static final int HTTP_STATUS = 409;

    /**
     * 기본 메시지를 사용하는 ConflictException을 생성합니다.
     * <p>
     * 메시지는 {@code "Conflict with existing resource"}로 설정됩니다.
     * </p>
     */
    public ConflictException(){
        super(HTTP_STATUS, "Conflict with existing resource");
    }

    /**
     * 사용자 정의 메시지를 포함하는 ConflictException을 생성합니다.
     *
     * @param message 예외와 함께 전달할 상세 메시지
     */
    public ConflictException(String message){
        super(HTTP_STATUS, message);
    }
}
