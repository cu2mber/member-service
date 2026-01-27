package com.cu2mber.memberservice.localgov.dto;

import com.cu2mber.memberservice.localgov.domain.LocalGov;

/**
 * 지자체(Local Government) 조회 결과를 전달하기 위한 응답 DTO입니다.
 * <p>
 * {@link LocalGov} 엔티티의 정보를 기반으로 생성되며,
 * 컨트롤러 계층에서 클라이언트로 반환되는
 * 읽기 전용 데이터 구조입니다.
 * </p>
 */
public record LocalGovResponse (
        Integer localNo,
        String localDistrict,
        String localName
){

    /**
     * {@link LocalGov} 엔티티를 {@link LocalGovResponse}로 변환하는 정적 팩토리 메서드입니다.
     * <p>
     * 엔티티를 직접 외부에 노출하지 않고,
     * 필요한 데이터만 선별하여 전달하기 위해 사용됩니다.
     * </p>
     *
     * @param localGov 변환 대상이 되는 LocalGov 엔티티
     * @return LocalGov 엔티티 정보를 담은 응답 DTO
     */
    public static LocalGovResponse from(LocalGov localGov){
        return new  LocalGovResponse(
                localGov.getLocalNo(),
                localGov.getLocalDistrict(),
                localGov.getLocalName()
        );
    }
}