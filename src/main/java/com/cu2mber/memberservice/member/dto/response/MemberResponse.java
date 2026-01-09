package com.cu2mber.memberservice.member.dto.response;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberResponse (

        long memberNo,

        String memberEmail,

        String memberName,

        MemberRole memberRole,

        String memberPhone,

        LocalDate memberBirth,

        AuthProvider authProvider,

        LocalDateTime createdAt
){
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberNo(),
                member.getMemberEmail(),
                member.getMemberName(),
                member.getMemberRole(),
                member.getMemberPhone(),
                member.getMemberBirth(),
                member.getAuthProvider(),
                member.getCreatedAt()
        );
    }
}
