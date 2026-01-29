package com.cu2mber.memberservice.common.exception;

/**
 * 회원의 상태가 도메인 규칙에 위배될 때 발생하는 예외 클래스입니다.
 * <p>
 * 활성화되지 않은 회원의 로그인 시도,
 * 이미 탈퇴한 회원의 접근 등
 * 허용되지 않은 회원 상태에서의 요청을 처리하기 위해 사용됩니다.
 * </p>
 * <p>
 * 주로 서비스 계층에서 발생하며,
 * {@link com.cu2mber.memberservice.common.advice.CommonAdvice}를 통해
 * 400 Bad Request 응답으로 변환됩니다.
 * </p>
 */
public class InvalidMemberStatusException extends RuntimeException {

    /**
     * 사용자 정의 메시지를 포함하는 InvalidMemberStatusException을 생성합니다.
     *
     * @param message 회원 상태가 유효하지 않은 이유를 설명하는 상세 메시지
     */
    public InvalidMemberStatusException(String message) {
        super(message);
    }
}