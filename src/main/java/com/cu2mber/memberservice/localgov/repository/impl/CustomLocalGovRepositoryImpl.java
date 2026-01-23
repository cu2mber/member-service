package com.cu2mber.memberservice.localgov.repository.impl;

import com.cu2mber.memberservice.localgov.dto.LocalGovResponse;
import com.cu2mber.memberservice.localgov.repository.CustomLocalGovRepository;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.cu2mber.memberservice.localgov.domain.QLocalGov.localGov;

import java.util.List;

@RequiredArgsConstructor
public class CustomLocalGovRepositoryImpl implements CustomLocalGovRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LocalGovResponse> findAllLocalGovs() {
        return jpaQueryFactory
                .select(Projections.constructor(
                        LocalGovResponse.class,
                        localGov.localNo,
                        localGov.localDistrict,
                        localGov.localName
                ))
                .from(localGov)
                .fetch();
    }

    @Override
    public List<String> findAllDistricts() {
        return jpaQueryFactory
                .select(localGov.localDistrict)
                .distinct()
                .from(localGov)
                .orderBy(localGov.localDistrict.asc())
                .fetch();
    }

    @Override
    public List<LocalGovResponse> findByLocalDistrict(String district) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        LocalGovResponse.class,
                        localGov.localNo,
                        localGov.localDistrict,
                        localGov.localName
                ))
                .from(localGov)
                .where(localGov.localDistrict.eq(district))
                .orderBy(localGov.localName.asc())
                .fetch();
    }
}