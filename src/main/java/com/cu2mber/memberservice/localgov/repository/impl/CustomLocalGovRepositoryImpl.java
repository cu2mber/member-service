package com.cu2mber.memberservice.localgov.repository.impl;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.repository.CustomLocalGovRepository;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.cu2mber.memberservice.localgov.domain.QLocalGov.localGov;

import java.util.List;

/**
 * 지자체(Local Government) 조회를 위한
 * QueryDSL 기반 커스텀 레포지토리 구현 클래스입니다.
 * <p>
 * 단순 조회 성능과 응답 전용 데이터 전달을 위해
 * 엔티티가 아닌 {@link LocalGovResponse} DTO를
 * 직접 조회하도록 설계되었습니다.
 * </p>
 */
@RequiredArgsConstructor
public class CustomLocalGovRepositoryImpl implements CustomLocalGovRepository {

    /**
     * QueryDSL 쿼리 생성을 담당하는 {@link JPAQueryFactory}입니다.
     */
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 전체 지자체 목록을 조회하는 메서드입니다.
     * <p>
     * 지자체 엔티티를 직접 반환하지 않고,
     * {@link LocalGovResponse} DTO로 프로젝션하여
     * 조회 성능과 계층 간 의존성을 최소화합니다.
     * </p>
     *
     * @return 전체 지자체 정보를 담은 LocalGovResponse 목록
     */
    @Override
    public List<LocalGovResponse> findAllLocalGovs() {
        return jpaQueryFactory
                .select(Projections.constructor(
                        LocalGovResponse.class,
                        localGov.localNo,
                        localGov.localDistrict,
                        localGov.localName
                ))
                .from(localGov)
                .fetch();
    }

    /**
     * 전체 구/군(district) 목록을 조회하는 메서드입니다.
     * <p>
     * 중복된 구/군 이름을 제거하기 위해 distinct 조회를 사용하며,
     * 오름차순 정렬된 결과를 반환합니다.
     * </p>
     *
     * @return 중복 제거된 전체 구/군 이름 목록
     */
    @Override
    public List<String> findAllDistricts() {
        return jpaQueryFactory
                .select(localGov.localDistrict)
                .distinct()
                .from(localGov)
                .orderBy(localGov.localDistrict.asc())
                .fetch();
    }

    /**
     * 특정 구/군(district)에 속한 지자체 목록을 조회하는 메서드입니다.
     * <p>
     * 요청된 구/군 이름과 일치하는 지자체만 조회하며,
     * 지자체 이름 기준으로 오름차순 정렬된 결과를 반환합니다.
     * </p>
     *
     * @param district 조회할 구/군 이름
     * @return 해당 구/군에 속한 지자체 정보를 담은 LocalGovResponse 목록
     */
    @Override
    public List<LocalGovResponse> findByLocalDistrict(String district) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        LocalGovResponse.class,
                        localGov.localNo,
                        localGov.localDistrict,
                        localGov.localName
                ))
                .from(localGov)
                .where(localGov.localDistrict.eq(district))
                .orderBy(localGov.localName.asc())
                .fetch();
    }
}