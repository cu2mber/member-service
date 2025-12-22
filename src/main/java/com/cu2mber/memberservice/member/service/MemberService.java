package com.cu2mber.memberservice.member.service;

import com.cu2mber.memberservice.member.dto.SignUpGovRequest;
import com.cu2mber.memberservice.member.dto.SignUpSocialUserRequest;
import com.cu2mber.memberservice.member.dto.SingUpUserRequest;

public interface MemberService {

    void signUpUser(SingUpUserRequest singUpUserRequest);

    void signUpGov(SignUpGovRequest signUpGovRequest);

    void signUpSocialUser(SignUpSocialUserRequest signUpSocialUserRequest);
}
