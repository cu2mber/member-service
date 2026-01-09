package com.cu2mber.memberservice.member.service;

import com.cu2mber.memberservice.member.dto.request.*;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.MemberRole;

public interface MemberService {

    void signUpUser(SignUpUserRequest signUpUserRequest);

    void signUpGov(SignUpGovRequest signUpGovRequest);

    MemberResponse socialLoginOrSignUp(SignUpSocialUserRequest request);

    MemberResponse getMember(long memberNo);

    void signInMember(SignInMemberRequest signInMemberRequest);

    void updateMember(long memberNo, MemberRole role, UpdateMemberRequest request);
}