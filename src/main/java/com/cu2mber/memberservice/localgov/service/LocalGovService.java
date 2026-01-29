package com.cu2mber.memberservice.localgov.service;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;

import java.util.List;

/**
 * 지자체(Local Government) 조회 기능을 제공하는
 * 서비스 인터페이스입니다.
 * <p>
 * 컨트롤러 계층에서 지자체 관련 조회 요청을 처리하기 위한
 * 비즈니스 로직의 계약을 정의합니다.
 * </p>
 */
public interface LocalGovService {

    /**
     * 전체 지자체 목록을 조회하는 기능입니다.
     *
     * @return 전체 지자체 정보를 담은 LocalGovResponse 목록
     */
    List<LocalGovResponse> getAllLocalGovs();

    /**
     * 전체 도/광역시(district) 목록을 조회하는 기능입니다.
     * <p>
     * 중복 제거된 도/광역시 목록을 반환하여,
     * 지자체 선택을 위한 기준 데이터로 활용됩니다.
     * </p>
     *
     * @return 전체 도/광역시 이름 목록
     */
    List<String> getAllDistricts();

    /**
     * 특정 도/광역시에 속한 시/군/구 목록을 조회하는 기능입니다.
     *
     * @param district 조회할 도/광역시 이름
     * @return 해당 도/광역시에 속한 지자체 정보를 담은 LocalGovResponse 목록
     */
    List<LocalGovResponse> getLocalNamesByDistrict(String district);
}
