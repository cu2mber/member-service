package com.cu2mber.memberservice.member.service;

import com.cu2mber.memberservice.member.dto.request.*;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.MemberRole;

/**
 * 회원 도메인의 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * <p>
 * 일반 회원, 지자체 회원, 소셜 회원에 대한
 * 가입, 로그인, 조회, 정보 수정 기능을 제공합니다.
 * </p>
 * <p>
 * 실제 로직은 구현체에서 회원 권한(MemberRole)과
 * 인증 방식에 따라 분기 처리됩니다.
 * </p>
 */
public interface MemberService {

    /**
     * 일반 회원 가입을 처리합니다.
     *
     * @param signUpUserRequest 일반 회원 가입 요청 정보
     */
    void signUpUser(SignUpUserRequest signUpUserRequest);

    /**
     * 지자체 회원 가입을 처리합니다.
     * <p>
     * 현재는 기본 구조만 제공하며,
     * 추후 관리자 승인 절차가 추가될 예정입니다.
     * </p>
     *
     * @param signUpGovRequest 지자체 회원 가입 요청 정보
     */
    void signUpGov(SignUpGovRequest signUpGovRequest);

    /**
     * 소셜 로그인 또는 소셜 회원 가입을 처리합니다.
     * <p>
     * 동일한 이메일과 인증 제공자(AuthProvider)를 기준으로
     * 회원이 존재하면 로그인, 존재하지 않으면 신규 가입을 수행합니다.
     * </p>
     *
     * @param request 소셜 로그인/회원 가입 요청 정보
     * @return 회원 정보 응답 DTO
     */
    MemberResponse socialLoginOrSignUp(SignUpSocialUserRequest request);

    /**
     * 회원 번호를 기준으로 회원 정보를 조회합니다.
     *
     * @param memberNo 회원 번호
     * @return 회원 정보 응답 DTO
     */
    MemberResponse getMember(long memberNo);

    /**
     * 일반 로그인(Local 로그인)을 처리합니다.
     *
     * @param signInMemberRequest 로그인 요청 정보
     */
    void signInMember(SignInMemberRequest signInMemberRequest);

    /**
     * 회원 권한에 따라 회원 정보를 수정합니다.
     *
     * @param memberNo 회원 번호
     * @param role 회원 권한
     * @param request 회원 정보 수정 요청
     */
    void updateMember(long memberNo, MemberRole role, UpdateMemberRequest request);
}