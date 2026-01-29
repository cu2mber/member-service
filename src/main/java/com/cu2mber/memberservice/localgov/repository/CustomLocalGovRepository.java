package com.cu2mber.memberservice.localgov.repository;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;

import java.util.List;

/**
 * 지자체(Local Government) 조회를 위한
 * 커스텀 레포지토리 인터페이스입니다.
 * <p>
 * 단순 CRUD를 넘어서는 조회 로직을 정의하며,
 * QueryDSL 기반 구현체를 통해
 * 응답 전용 DTO를 직접 조회하도록 설계되었습니다.
 * </p>
 */
public interface CustomLocalGovRepository {

    /**
     * 전체 지자체 목록을 조회하는 메서드입니다.
     *
     * @return 전체 지자체 정보를 담은 LocalGovResponse 목록
     */
    List<LocalGovResponse> findAllLocalGovs();

    /**
     * 전체 구/군(district) 목록을 조회하는 메서드입니다.
     * <p>
     * 중복 제거된 구/군 이름 목록을 반환하며,
     * 지자체 선택 UI 등의 데이터로 활용됩니다.
     * </p>
     *
     * @return 전체 구/군 이름 목록
     */
    List<String> findAllDistricts();

    /**
     * 특정 구/군(district)에 속한 지자체 목록을 조회하는 메서드입니다.
     *
     * @param district 조회할 구/군 이름
     * @return 해당 구/군에 속한 지자체 정보를 담은 LocalGovResponse 목록
     */
    List<LocalGovResponse> findByLocalDistrict(String district);
}
