package com.cu2mber.memberservice.localgov.repository;

import com.cu2mber.memberservice.localgov.domain.LocalGov;
import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocalGovRepositoryTest {

    @Autowired
    private LocalGovRepository localGovRepository;

    @TestConfiguration
    static class TestConfig{
        @PersistenceContext
        private EntityManager em;

        @Bean
        public JPAQueryFactory queryFactory(){
            return new JPAQueryFactory(em);
        }
    }

    @BeforeEach
    void setUp(){
        localGovRepository.save(LocalGov.ofNewGov("경상남도", "창원특례시"));
        localGovRepository.save(LocalGov.ofNewGov("경상남도", "김해시"));
        localGovRepository.save(LocalGov.ofNewGov("경상남도", "진주시"));
        localGovRepository.save(LocalGov.ofNewGov("경기도", "성남시"));
        localGovRepository.save(LocalGov.ofNewGov("서울특별시", "강남구"));
    }

    @Test
    @DisplayName("전체 지자체 목록 조회")
    void findAllLocalGovs(){
        List<LocalGovResponse> localGovs = localGovRepository.findAllLocalGovs();

        LocalGovResponse localGov1 = localGovs.get(0);
        LocalGovResponse localGov2 = localGovs.get(1);
        LocalGovResponse localGov3 = localGovs.get(2);
        LocalGovResponse localGov4 = localGovs.get(3);
        LocalGovResponse localGov5 = localGovs.get(4);

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals("경상남도", localGov1.localDistrict());
                    Assertions.assertEquals("창원특례시", localGov1.localName());
                },
                () -> {
                    Assertions.assertEquals("경상남도", localGov2.localDistrict());
                    Assertions.assertEquals("김해시", localGov2.localName());
                },
                () -> {
                    Assertions.assertEquals("경상남도", localGov3.localDistrict());
                    Assertions.assertEquals("진주시", localGov3.localName());
                },
                () -> {
                    Assertions.assertEquals("경기도", localGov4.localDistrict());
                    Assertions.assertEquals("성남시", localGov4.localName());
                },
                () -> {
                    Assertions.assertEquals("서울특별시", localGov5.localDistrict());
                    Assertions.assertEquals("강남구", localGov5.localName());
                }
        );
    }

    @Test
    @DisplayName("중복 제거된 도/광역시 목록 조회 및 정렬 확인")
    void findAllDistricts() {
        List<String> districts = localGovRepository.findAllDistricts();

        Assertions.assertEquals(3, districts.size());
        Assertions.assertAll(
                () -> {
                    assertEquals("경기도", districts.get(0));
                    assertEquals("경상남도", districts.get(1));
                    assertEquals("서울특별시", districts.get(2));
                }
        );
    }

    @Test
    @DisplayName("특정 도/광역시에 속한 시/군/구 목록 조회")
    void findAllDistrictsByName() {
        String district = "경상남도";

        List<LocalGovResponse> result = localGovRepository.findByLocalDistrict(district);

        Assertions.assertEquals(3, result.size());
        Assertions.assertAll(
                () -> {
                    assertEquals("김해시", result.get(0).localName());
                    assertEquals("진주시", result.get(1).localName());
                    assertEquals("창원특례시", result.get(2).localName());
                }
        );
    }
}