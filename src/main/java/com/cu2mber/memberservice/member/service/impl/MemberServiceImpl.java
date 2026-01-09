package com.cu2mber.memberservice.member.service.impl;

import com.cu2mber.memberservice.common.exception.BadRequestException;
import com.cu2mber.memberservice.common.exception.ConflictException;
import com.cu2mber.memberservice.common.exception.NotFoundException;
import com.cu2mber.memberservice.common.exception.UnauthorizedException;
import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.dto.request.*;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import com.cu2mber.memberservice.member.repository.MemberRepository;
import com.cu2mber.memberservice.member.service.MemberService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUpUser(SignUpUserRequest signUpUserRequest) {
        log.debug("일반 회원가입 시작! 회원 정보: {}", signUpUserRequest);

        validateDuplicateEmail(signUpUserRequest.memberEmail());

        String encodePassword = passwordEncoder.encode(signUpUserRequest.memberPwd());
        LocalDate memberBirth = parseBirth(signUpUserRequest.memberBirth());

        Member member = Member.ofNewUser(
                    signUpUserRequest.memberName(),
                    signUpUserRequest.memberEmail(),
                    encodePassword,
                    signUpUserRequest.memberPhone(),
                    memberBirth
        );

        memberRepository.save(member);
    }

    @Override
    public void signUpGov(SignUpGovRequest signUpGovRequest) {
        log.debug("지자체 회원가입 시작! 회원 정보: {}", signUpGovRequest);

        // 관리자 요청 로직 구현 후 완성예정
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponse socialLoginOrSignUp(SignUpSocialUserRequest request) {
        log.debug("소셜 회원가입 시작! 회원 정보: {}", request);

        Member member = memberRepository
                .findByMemberEmailAndAuthProvider(request.memberEmail(), request.provider())
                .orElseGet(() -> {
                    Member newMember = Member.ofNewSocialUser(
                            request.memberName(),
                            request.memberEmail(),
                            "password",
                            request.memberPhone(),
                            parseBirth(request.memberBirth()),
                            request.provider(),
                            request.providerId()
                    );
                    return memberRepository.save(newMember);
                });
        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponse getMember(long memberNo) {
        log.debug("회원조회 시작! 회원번호 : {}", memberNo);

        Member member = memberRepository.findByMemberNo(memberNo)
                .orElseThrow(() -> new NotFoundException("해당 회원번호의 회원을 찾을 수 없습니다."));

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    @Override
    public void signInMember(SignInMemberRequest request) {
        log.debug("로그인 시작! 회원 이메일 : {}", request.toString());

        Member getMember = memberRepository
                .findByMemberEmailAndWithdrawalAtIsNull(request.memberEmail())
                .orElseThrow(() -> new NotFoundException("해당 memberEmail의 회원을 찾을 수 없습니다."));

        if (getMember.getAuthProvider() != AuthProvider.LOCAL) {
            throw new UnauthorizedException("소셜로그인 사용자입니다.");
        }

        if(!passwordEncoder.matches(request.memberPwd(), getMember.getMemberPwd())){
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void updateMember(long memberNo, MemberRole role, UpdateMemberRequest request) {
        switch (role) {
            case USER -> updateUser(memberNo, request);
            case GOV -> updateGov(request);
            default -> throw new UnauthorizedException("수정 권한이 없습니다.");
        }
    }

    private void updateUser(long memberNo, UpdateMemberRequest request) {
        Member member = memberRepository
                .findByMemberNoAndWithdrawalAtIsNull(memberNo)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다."));

        validateUpdate(request);

        String encodedPwd = null;

        // 비밀번호 변경
        if(request.newPassword() != null){

            if(!request.newPassword().equals(request.confirmPassword())) {
                throw new BadRequestException("새 비밀번호가 일치하지 않습니다.");
            }

            encodedPwd = passwordEncoder.encode(request.newPassword());
        }

        member.updateUser(
                request.memberName(),
                encodedPwd,
                request.memberPhone()
        );
    }

    private void updateGov(UpdateMemberRequest request) {
        // 추후 관리자 요청 로직 구현 후 완성 예정
    }

    private void validateDuplicateEmail(String email){
        if(memberRepository.existsByMemberEmailAndWithdrawalAtIsNull(email)){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }
    }

    private LocalDate parseBirth(String birth){
        // 기본 형식 검증 (혹시 모를 방어)
        if (birth == null || !birth.matches("^\\d{6}$")) {
            throw new BadRequestException("생년월일은 6자리 숫자(yyMMdd)여야 합니다.");
        }

        int yy = Integer.parseInt(birth.substring(0, 2));
        int mmdd = Integer.parseInt(birth.substring(2));

        // 현재 연도 기준 계산
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentCentury = (currentYear / 100) * 100;
        int currentYearTwoDigits = currentYear % 100;

        int fullYear = (yy <= currentYearTwoDigits)
                ? currentCentury + yy
                : currentCentury - 100 + yy;

        // LocalDate 생성
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(
                    fullYear + String.format("%04d", mmdd),
                    DateTimeFormatter.ofPattern("yyyyMMdd")
            );
        } catch (DateTimeParseException e) {
            throw new BadRequestException("유효하지 않은 생년월일입니다.");
        }

        // 미래 날짜 방어
        if (birthDate.isAfter(now)) {
            throw new BadRequestException("생년월일이 미래일 수 없습니다.");
        }

        return birthDate;
    }

    private void validateUpdate(UpdateMemberRequest request) {
        if(request.memberName() == null
                && request.newPassword() == null
                && request.confirmPassword() == null
                && request.memberPhone() == null)
        {
            throw new BadRequestException("수정할 정보가 없습니다.");
        }
    }
}
