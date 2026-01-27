package com.cu2mber.memberservice.localgov.repository;

import com.cu2mber.memberservice.localgov.domain.LocalGov;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 지자체(Local Government) 엔티티를 관리하는 레포지토리 인터페이스입니다.
 * <p>
 * {@link JpaRepository}를 상속하여 기본적인 CRUD 기능을 제공하며,
 * {@link CustomLocalGovRepository}를 함께 상속하여
 * QueryDSL 기반의 커스텀 조회 기능을 사용할 수 있도록 구성되었습니다.
 * </p>
 */
public interface LocalGovRepository extends JpaRepository<LocalGov, Integer>, CustomLocalGovRepository {

}