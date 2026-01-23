package com.cu2mber.memberservice.localgov.service.impl;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.repository.LocalGovRepository;
import com.cu2mber.memberservice.localgov.service.LocalGovService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LocalGovServiceImpl implements LocalGovService {

    private final LocalGovRepository localGovRepository;

    // 테이블 전체 조회
    @Override
    public List<LocalGovResponse> getAllLocalGovs() {
        log.debug("테이블 전체 조회!");
        return localGovRepository.findAllLocalGovs();
    }

    // 전체 도/광역시 목록 조회
    @Override
    public List<String> getAllDistricts() {
        log.debug("전체 도/광역시 조회!");
        return localGovRepository.findAllDistricts();
    }

    // 특정 지역(도/광역시)에 속한 상세 시/군/구 목록 조회
    @Override
    public List<LocalGovResponse> getLocalNamesByDistrict(String district) {
        log.debug("{}에 속한 시/군/구 조회!", district);
        return localGovRepository.findByLocalDistrict(district);
    }
}