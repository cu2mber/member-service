package com.cu2mber.memberservice.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 보안 관련 공통 설정을 담당하는 Configuration 클래스입니다.
 * <p>
 * 애플리케이션 전반에서 사용할 {@link PasswordEncoder} 빈을 등록하여
 * 비밀번호 암호화 방식을 일관되게 관리합니다.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 비밀번호 암호화를 위한 {@link PasswordEncoder} 빈을 등록합니다.
     * <p>
     * {@link BCryptPasswordEncoder}를 사용하여
     * 단방향 해시 기반의 안전한 비밀번호 암호화를 제공합니다.
     * </p>
     *
     * @return BCrypt 기반의 PasswordEncoder 구현체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}