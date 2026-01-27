package com.cu2mber.memberservice.localgov.controller;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.service.LocalGovService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 지자체(Local Government) 관련 조회 요청을 처리하는 컨트롤러입니다.
 * <p>
 * 지자체 목록 조회, 구/군(district) 기준 지자체 조회,
 * 전체 구/군 목록 조회 기능을 제공합니다.
 * </p>
 */
@RestController
@RequestMapping("/local-govs")
@RequiredArgsConstructor
public class LocalGovController {

    /**
     * 지자체 관련 비즈니스 로직을 처리하는 서비스 객체입니다.
     */
    private final LocalGovService localGovService;

    /**
     * 전체 지자체 목록을 조회하는 API입니다.
     * <p>
     * 등록된 모든 지자체 정보를 조회하여
     * {@link LocalGovResponse} 목록 형태로 반환합니다.
     * </p>
     *
     * @return 전체 지자체 목록을 포함한 200 OK 응답
     */
    @GetMapping
    public ResponseEntity<List<LocalGovResponse>> getAllLocalGovs() {
        return ResponseEntity
                .ok(localGovService.getAllLocalGovs());
    }

    /**
     * 구/군(district)을 기준으로 지자체 목록을 조회하는 API입니다.
     * <p>
     * 요청 파라미터로 전달된 district 값에 해당하는
     * 지자체 이름 목록을 반환합니다.
     * </p>
     *
     * @param district 조회할 구/군 이름
     * @return 해당 구/군에 속한 지자체 목록을 포함한 200 OK 응답
     */
    @GetMapping(params = "district")
    public ResponseEntity<List<LocalGovResponse>> getLocalNameByDistrict(@RequestParam("district") String district) {
        return ResponseEntity
                .ok(localGovService.getLocalNamesByDistrict(district));
    }

    /**
     * 전체 구/군(district) 목록을 조회하는 API입니다.
     * <p>
     * 중복되지 않은 구/군 이름 목록을 조회하여
     * 클라이언트에서 선택 목록 등으로 활용할 수 있도록 제공합니다.
     * </p>
     *
     * @return 전체 구/군 목록을 포함한 200 OK 응답
     */
    @GetMapping("/districts")
    public ResponseEntity<List<String>> getDistricts() {
        return ResponseEntity
                .ok(localGovService.getAllDistricts());
    }
}
