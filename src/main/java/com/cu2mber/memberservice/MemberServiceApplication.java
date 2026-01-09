package com.cu2mber.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 회원 서비스(Member Service)의 애플리케이션 시작 클래스입니다.
 * <p>
 * Spring Boot 애플리케이션으로 실행되며,
 * 서비스 디스커버리(Eureka 등)에 등록되어
 * 다른 마이크로서비스와 연동됩니다.
 * </p>
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MemberServiceApplication {

    /**
     * Member Service 애플리케이션의 진입점입니다.
     *
     * @param args 애플리케이션 실행 시 전달되는 인자
     */
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

}