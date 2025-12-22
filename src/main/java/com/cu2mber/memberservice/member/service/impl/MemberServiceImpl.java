package com.cu2mber.memberservice.member.service.impl;

import com.cu2mber.memberservice.common.exception.ConflictException;
import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.dto.SignUpGovRequest;
import com.cu2mber.memberservice.member.dto.SignUpSocialUserRequest;
import com.cu2mber.memberservice.member.dto.SingUpUserRequest;
import com.cu2mber.memberservice.member.repository.MemberRepository;
import com.cu2mber.memberservice.member.service.MemberService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUpUser(SingUpUserRequest singUpUserRequest) {
        log.debug("회원가입 시작! 회원 정보: {}", singUpUserRequest);

        boolean isExistsEmail = memberRepository.existsByMemberEmail(singUpUserRequest.memberEmail());

        if(isExistsEmail){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }

        String encodePassword = passwordEncoder.encode(singUpUserRequest.memberPwd());

        LocalDate memberBirth = parseBirth(singUpUserRequest.memberBirth());

        Member member = Member.ofNewUser(
                    singUpUserRequest.memberName(),
                    singUpUserRequest.memberEmail(),
                    encodePassword,
                    singUpUserRequest.memberPhone(),
                    memberBirth
        );

        memberRepository.save(member);
    }

    @Override
    public void signUpGov(SignUpGovRequest signUpGovRequest) {
        log.debug("회원가입 시작! 회원 정보: {}", signUpGovRequest);

        boolean isExistsEmail = memberRepository.existsByMemberEmail(signUpGovRequest.memberEmail());

        if(isExistsEmail){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }

        Member member = Member.ofNewGov(
                signUpGovRequest.memberName(),
                signUpGovRequest.memberEmail(),
                signUpGovRequest.memberPwd(),
                signUpGovRequest.memberPhone()
        );

        memberRepository.save(member);
    }

    @Override
    public void signUpSocialUser(SignUpSocialUserRequest signUpSocialUserRequest) {
        log.debug("회원가입 시작! 회원 정보: {}", signUpSocialUserRequest);

        boolean isExistsEmail = memberRepository.existsByMemberEmail(signUpSocialUserRequest.memberEmail());

        if(isExistsEmail){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }

        LocalDate memberBirth = parseBirth(signUpSocialUserRequest.memberBirth());

        Member member = Member.ofNewSocialUser(
                signUpSocialUserRequest.memberName(),
                signUpSocialUserRequest.memberEmail(),
                signUpSocialUserRequest.memberPwd(),
                signUpSocialUserRequest.memberPhone(),
                memberBirth,
                signUpSocialUserRequest.provider(),
                signUpSocialUserRequest.providerId()
        );

        memberRepository.save(member);
    }

    private LocalDate parseBirth(String birth){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(birth, formatter);
    }
}
