package com.cu2mber.memberservice.localgov.service;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.repository.LocalGovRepository;
import com.cu2mber.memberservice.localgov.service.impl.LocalGovServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocalGovServiceTest {

    @Mock
    private LocalGovRepository localGovRepository;

    @InjectMocks
    private LocalGovServiceImpl localGovService;

    @Test
    @DisplayName("전체 지자체 목록 조회")
    void getAllLocalGovs() {
        List<LocalGovResponse> mockList = List.of(
                new LocalGovResponse(1, "경기도", "성남시"),
                new LocalGovResponse(3, "경상남도", "김해시"),
                new LocalGovResponse(2, "서울특별시", "강남구")
        );
        given(localGovRepository.findAllLocalGovs()).willReturn(mockList);

        List<LocalGovResponse> result = localGovService.getAllLocalGovs();

        Assertions.assertEquals(3, result.size());
        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals("경기도", result.get(0).localDistrict());
                    Assertions.assertEquals("성남시", result.get(0).localName());
                },
                () -> {
                    Assertions.assertEquals("경상남도", result.get(1).localDistrict());
                    Assertions.assertEquals("김해시", result.get(1).localName());
                },
                () -> {
                    Assertions.assertEquals("서울특별시", result.get(2).localDistrict());
                    Assertions.assertEquals("강남구", result.get(2).localName());
                }
        );

        verify(localGovRepository).findAllLocalGovs();
    }

    @Test
    @DisplayName("전체 도/광역시 목록 조회")
    void getAllDistricts() {
        List<String> mockDistricts = List.of("경기도", "경상남도", "서울특별시");
        given(localGovRepository.findAllDistricts()).willReturn(mockDistricts);

        List<String> result = localGovService.getAllDistricts();

        Assertions.assertEquals(3, result.size());
        Assertions.assertAll(
                () -> {
                    Assertions.assertTrue(result.contains("경상남도"));
                    Assertions.assertTrue(result.contains("경기도"));
                    Assertions.assertTrue(result.contains("서울특별시"));
                }
        );

        verify(localGovRepository).findAllDistricts();
    }

    @Test
    @DisplayName("특정 구역에 속한 지자체 목록 조회")
    void getLocalNamesByDistrict() {
        String district = "경상남도";
        List<LocalGovResponse> mockList = List.of(
                new LocalGovResponse(1, "경상남도", "창원시"),
                new LocalGovResponse(2, "경상남도", "진주시")
        );
        given(localGovRepository.findByLocalDistrict(district)).willReturn(mockList);

        List<LocalGovResponse> result = localGovService.getLocalNamesByDistrict(district);

        Assertions.assertEquals(2, result.size());
        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals("창원시", result.get(0).localName());
                    Assertions.assertEquals("진주시", result.get(1).localName());
                }
        );

        verify(localGovRepository).findByLocalDistrict(anyString());
    }
}