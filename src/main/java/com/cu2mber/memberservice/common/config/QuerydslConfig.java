package com.cu2mber.memberservice.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 설정을 담당하는 Configuration 클래스입니다.
 * <p>
 * JPA {@link EntityManager}를 기반으로
 * {@link JPAQueryFactory}를 빈으로 등록하여,
 * Repository 계층에서 QueryDSL을 사용할 수 있도록 합니다.
 * </p>
 */
@Configuration
public class QuerydslConfig {

    /**
     * JPA 영속성 컨텍스트에서 관리되는 {@link EntityManager}입니다.
     * <p>
     * QueryDSL 쿼리 생성 시 사용되며,
     * 트랜잭션 범위 내에서 엔티티의 상태를 관리합니다.
     * </p>
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * {@link JPAQueryFactory}를 스프링 빈으로 등록하는 메서드입니다.
     * <p>
     * QueryDSL을 사용하여 타입 안전한 JPQL 쿼리를 작성할 수 있도록 하며,
     * Repository 구현체에서 주입받아 사용됩니다.
     * </p>
     *
     * @return {@link EntityManager}를 기반으로 생성된 JPAQueryFactory 인스턴스
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
