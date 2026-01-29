package com.cu2mber.memberservice.localgov.service.impl;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.repository.LocalGovRepository;
import com.cu2mber.memberservice.localgov.service.LocalGovService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 지자체(Local Government) 조회 관련 비즈니스 로직을 처리하는
 * 서비스 구현 클래스입니다.
 * <p>
 * 지자체 정보 조회에 대한 트랜잭션 경계를 관리하며,
 * {@link LocalGovRepository}를 통해
 * 데이터 접근 계층과 연동합니다.
 * </p>
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocalGovServiceImpl implements LocalGovService {

    /**
     * 지자체 엔티티 및 조회 기능을 제공하는 레포지토리입니다.
     */
    private final LocalGovRepository localGovRepository;

    /**
     * 전체 지자체 목록을 조회하는 메서드입니다.
     * <p>
     * 모든 지자체 데이터를 조회하여
     * {@link LocalGovResponse} 목록 형태로 반환합니다.
     * </p>
     *
     * @return 전체 지자체 정보를 담은 LocalGovResponse 목록
     */
    @Override
    public List<LocalGovResponse> getAllLocalGovs() {
        log.debug("테이블 전체 조회!");
        return localGovRepository.findAllLocalGovs();
    }

    /**
     * 전체 도/광역시(district) 목록을 조회하는 메서드입니다.
     * <p>
     * 중복 제거된 도/광역시 목록을 조회하여
     * 클라이언트에서 선택 목록 등으로 활용할 수 있도록 제공합니다.
     * </p>
     *
     * @return 전체 도/광역시 이름 목록
     */
    @Override
    public List<String> getAllDistricts() {
        log.debug("전체 도/광역시 조회!");
        return localGovRepository.findAllDistricts();
    }

    /**
     * 특정 도/광역시에 속한 시/군/구 목록을 조회하는 메서드입니다.
     * <p>
     * 전달받은 도/광역시 이름을 기준으로
     * 해당 지역에 포함된 지자체 목록을 조회합니다.
     * </p>
     *
     * @param district 조회할 도/광역시 이름
     * @return 해당 도/광역시에 속한 지자체 정보를 담은 LocalGovResponse 목록
     */
    @Override
    public List<LocalGovResponse> getLocalNamesByDistrict(String district) {
        log.debug("{}에 속한 시/군/구 조회!", district);
        return localGovRepository.findByLocalDistrict(district);
    }
}