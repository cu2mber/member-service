package com.cu2mber.memberservice.localgov.repository;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;

import java.util.List;

public interface CustomLocalGovRepository {
    List<LocalGovResponse> findAllLocalGovs();

    List<String> findAllDistricts();

    List<LocalGovResponse> findByLocalDistrict(String district);
}
