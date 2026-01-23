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

@RestController
@RequestMapping("/local-govs")
@RequiredArgsConstructor
public class LocalGovController {

    private final LocalGovService localGovService;

    @GetMapping
    public ResponseEntity<List<LocalGovResponse>> getAllLocalGovs() {
        return ResponseEntity
                .ok(localGovService.getAllLocalGovs());
    }

    @GetMapping(params = "district")
    public ResponseEntity<List<LocalGovResponse>> getLocalNameByDistrict(@RequestParam("district") String district) {
        return ResponseEntity
                .ok(localGovService.getLocalNamesByDistrict(district));
    }

    @GetMapping("/districts")
    public ResponseEntity<List<String>> getDistricts() {
        return ResponseEntity
                .ok(localGovService.getAllDistricts());
    }
}
