package com.cu2mber.memberservice.localgov.service;

import com.cu2mber.memberservice.localgov.domain.LocalGov;
import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;

import java.util.List;

public interface LocalGovService {
    List<LocalGovResponse> getAllLocalGovs();

    List<String> getAllDistricts();

    List<LocalGovResponse> getLocalNamesByDistrict(String district);
}
