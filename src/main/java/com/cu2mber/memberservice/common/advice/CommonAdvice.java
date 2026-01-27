package com.cu2mber.memberservice.common.advice;

import com.cu2mber.memberservice.common.exception.CommonHttpException;
import com.cu2mber.memberservice.common.exception.InvalidMemberStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리를 담당하는 Advice 클래스입니다.
 * <p>
 * 컨트롤러 계층에서 발생하는 예외를 공통적으로 처리하여
 * 일관된 HTTP 응답을 반환합니다.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class CommonAdvice {

    /**
     * {@link InvalidMemberStatusException} 예외를 처리하는 메서드입니다.
     * <p>
     * 회원의 상태가 도메인 규칙에 위배되는 경우 발생하며,
     * 클라이언트 요청이 유효하지 않음을 의미하므로
     * 400 Bad Request 응답을 반환합니다.
     * </p>
     *
     * @param e 회원 상태 규칙 위반으로 발생한 예외
     * @return 예외 메시지를 포함한 400 Bad Request 응답
     */
    @ExceptionHandler(InvalidMemberStatusException.class)
    public ResponseEntity<String> invalidMemberStatusHandler(
            InvalidMemberStatusException e
    ) {
        log.warn("도메인 규칙 위반: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    /**
     * {@link BindException} 예외를 처리하는 메서드입니다.
     * <p>
     * 요청 파라미터 바인딩 또는 검증(@Valid) 실패 시 발생하며,
     * 필드별 에러 메시지를 조합하여 400 Bad Request 응답을 반환합니다.
     * </p>
     *
     * @param e 바인딩 또는 검증 실패로 발생한 예외
     * @return 필드 에러 메시지를 포함한 400 Bad Request 응답
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> bindExceptionHandler(BindException e) {
        log.warn("BindException 발생: {}", e.getMessage());

        StringBuilder errorMessage = new StringBuilder("Bad Request: ");

        for (FieldError fieldError : e.getFieldErrors()) {
            errorMessage.append(fieldError.getField())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }

        return ResponseEntity
                .badRequest()
                .body(errorMessage.toString());
    }

    /**
     * {@link CommonHttpException} 예외를 처리하는 메서드입니다.
     * <p>
     * 커스텀 예외에 포함된 HTTP 상태 코드를 기반으로
     * 적절한 상태 코드와 메시지를 응답으로 반환합니다.
     * </p>
     *
     * @param e 발생한 CommonHttpException 예외 객체
     * @return 예외에 정의된 상태 코드와 메시지를 포함한 응답
     */
    @ExceptionHandler(CommonHttpException.class)
    public ResponseEntity<String> commonExceptionHandler(CommonHttpException e) {
        log.warn("CommonHttpException 발생: {}", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body("CommonException: " + e.getMessage());
    }

    /**
     * 처리되지 않은 모든 예외를 처리하는 메서드입니다.
     * <p>
     * 예상하지 못한 예외 발생 시 500 Internal Server Error로 응답하며,
     * 클라이언트에는 내부 구현 정보가 노출되지 않도록
     * 일반적인 메시지를 반환합니다.
     * </p>
     *
     * @param e 처리되지 않은 모든 예외(Throwable)
     * @return 500 Internal Server Error 응답
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> exceptionHandler(Throwable e) {
        log.error("Internal Server Error: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
}
