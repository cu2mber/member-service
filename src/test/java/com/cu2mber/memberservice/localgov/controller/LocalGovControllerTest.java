package com.cu2mber.memberservice.localgov.controller;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.service.LocalGovService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocalGovController.class)
class LocalGovControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocalGovService localGovService;

    @Test
    @WithMockUser
    @DisplayName("모든 지자체 목록 조회")
    void getAllLocalGov() throws Exception {

        LocalGovResponse response1 = new LocalGovResponse(1, "경기도", "성남시");
        LocalGovResponse response2 = new LocalGovResponse(2, "경상남도", "김해시");
        LocalGovResponse response3 = new LocalGovResponse(3, "경상남도", "창원시");
        LocalGovResponse response4 = new LocalGovResponse(4, "서울특별시", "강남구");

        given(localGovService.getAllLocalGovs()).willReturn(List.of(response1, response2, response3, response4));

        mockMvc.perform(get("/local-govs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].localName").value("성남시"))
                .andExpect(jsonPath("$[1].localName").value("김해시"))
                .andExpect(jsonPath("$[2].localName").value("창원시"))
                .andExpect(jsonPath("$[3].localName").value("강남구"));
    }

    @Test
    @WithMockUser
    @DisplayName("도/광역시 목록 조회")
    void getDistricts() throws Exception {

        LocalGovResponse response1 = new LocalGovResponse(1, "경기도", "성남시");
        LocalGovResponse response2 = new LocalGovResponse(2, "경상남도", "김해시");
        LocalGovResponse response3 = new LocalGovResponse(3, "서울특별시", "강남구");

        given(localGovService.getAllDistricts()).willReturn(List.of(
                response1.localDistrict(),
                response2.localDistrict(),
                response3.localDistrict()
        ));

        // when & then
        mockMvc.perform(get("/local-govs/districts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("경기도"))
                .andExpect(jsonPath("$[1]").value("경상남도"))
                .andExpect(jsonPath("$[2]").value("서울특별시"));
    }

    @Test
    @WithMockUser
    @DisplayName("특정 지역에 속한 지자체 목록 조회")
    void getLocalNameByDistrict() throws Exception {

        String district = "경상남도";
        LocalGovResponse response1 = new LocalGovResponse(1, "경상남도", "김해시");
        LocalGovResponse response2 = new LocalGovResponse(2, "경상남도", "창원시");

        given(localGovService.getLocalNamesByDistrict(district)).willReturn(List.of(response1, response2));

        mockMvc.perform(get("/local-govs")
                        .param("district", district)) // 쿼리 파라미터 전달
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localDistrict").value(district))
                .andExpect(jsonPath("$[0].localName").value("김해시"))
                .andExpect(jsonPath("$[1].localDistrict").value(district))
                .andExpect(jsonPath("$[1].localName").value("창원시"));
    }
}