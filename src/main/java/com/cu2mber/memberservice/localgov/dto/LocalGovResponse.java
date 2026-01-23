package com.cu2mber.memberservice.localgov.dto;

import com.cu2mber.memberservice.localgov.domain.LocalGov;

public record LocalGovResponse (
        Integer localNo,
        String localDistrict,
        String localName
){
    public static LocalGovResponse from(LocalGov localGov){
        return new  LocalGovResponse(
                localGov.getLocalNo(),
                localGov.getLocalDistrict(),
                localGov.getLocalName()
        );
    }
}