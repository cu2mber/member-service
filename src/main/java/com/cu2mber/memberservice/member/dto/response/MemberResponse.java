package com.cu2mber.memberservice.member.dto.response;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원 조회 결과를 반환하기 위한 응답 DTO입니다.
 * <p>
 * {@link Member} 엔티티의 정보를 외부로 노출 가능한 형태로 변환하여 제공합니다.
 * </p>
 *
 * <p>
 * 엔티티를 직접 반환하지 않고 DTO로 변환함으로써
 * 도메인 보호 및 응답 데이터 구조를 명확히 합니다.
 * </p>
 *
 * @param memberNo     회원 고유 번호
 * @param memberEmail  회원 이메일
 * @param memberName   회원 이름
 * @param memberRole   회원 권한
 * @param memberPhone  회원 휴대폰 번호
 * @param memberBirth  회원 생년월일
 * @param authProvider 가입/인증 제공자
 * @param createdAt    회원 가입 일시
 */
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

    /**
     * {@link Member} 엔티티를 {@link MemberResponse} DTO로 변환합니다.
     *
     * @param member 회원 엔티티
     * @return 변환된 회원 응답 DTO
     */
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
