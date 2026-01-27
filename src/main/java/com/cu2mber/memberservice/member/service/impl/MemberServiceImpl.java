package com.cu2mber.memberservice.member.service.impl;

import com.cu2mber.memberservice.common.exception.*;
import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.dto.request.*;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import com.cu2mber.memberservice.member.enums.MemberStatus;
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
import java.util.Optional;
import java.util.UUID;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스 구현체입니다.
 * <p>
 * 일반 회원 / 지자체 회원 / 소셜 회원의 가입, 로그인, 조회, 수정 기능을 담당하며
 * 회원 상태(탈퇴 여부), 인증 제공자(AuthProvider), 권한(MemberRole)을 기준으로
 * 각 로직을 분기 처리합니다.
 * </p>
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 일반 회원 가입을 처리합니다.
     * <p>
     * 이메일 중복 여부를 검증하고,
     * 비밀번호 암호화 및 생년월일 파싱 후 회원을 저장합니다.
     * </p>
     *
     * @param signUpUserRequest 일반 회원 가입 요청 정보
     * @throws ConflictException 이메일이 이미 존재하는 경우
     * @throws BadRequestException 생년월일 형식이 올바르지 않은 경우
     */
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

    /**
     * 지자체 회원 가입을 처리합니다.
     * <p>
     * 현재는 기본 구조만 존재하며,
     * 추후 관리자 승인 로직이 추가될 예정입니다.
     * </p>
     *
     * @param request 지자체 회원 가입 요청 정보
     */
    @Override
    public void signUpGov(SignUpGovRequest request) {
        log.debug("지자체 회원가입 시작! 회원 정보: {}", request);

        validateDuplicateEmail(request.memberEmail());

        String encodePassword = passwordEncoder.encode(request.memberPwd());

        Member govMember = Member.ofNewGov(
                request.memberName(),
                request.memberEmail(),
                encodePassword,
                request.memberPhone()
        );

        memberRepository.save(govMember);
    }

    /**
     * 소셜 로그인 또는 소셜 회원 가입을 처리합니다.
     * <p>
     * 동일한 이메일과 인증 제공자(AuthProvider)를 가진 회원이 존재하면 로그인 처리하고,
     * 존재하지 않으면 신규 회원으로 가입 처리합니다.
     * </p>
     *
     * @param request 소셜 회원 가입/로그인 요청 정보
     * @return 회원 정보 응답 DTO
     */
    @Transactional
    @Override
    public MemberResponse socialLoginOrSignUp(SignUpSocialUserRequest request) {
        log.debug("소셜 로그인/가입 시작! 회원 정보: {}", request);

        Optional<Member> existingMember = memberRepository.findByMemberEmailAndMemberStatus(request.memberEmail(), MemberStatus.ACTIVE);

        if (existingMember.isPresent()) {
            Member member = existingMember.get();
            AuthProvider joinedProvider = member.getAuthProvider();

            validateAccountStatusForLogin(member);

            if (joinedProvider != request.provider()) {
                String providerName = (joinedProvider == AuthProvider.LOCAL) ? "일반" : joinedProvider.name();
                throw new ConflictException("해당 이메일은 이미 " + providerName + "계정으로 가입되어 있습니다.");
            }

            return MemberResponse.from(member);
        }

        log.debug("신규 소셜 회원 가입 진행: {}", request.memberEmail());
        Member newMember = Member.ofNewSocialUser(
                request.memberName(),
                request.memberEmail(),
                passwordEncoder.encode(UUID.randomUUID().toString()),
                request.memberPhone(),
                parseBirth(request.memberBirth()),
                request.provider(),
                request.providerId()
        );

        return MemberResponse.from(memberRepository.save(newMember));
    }

    /**
     * 회원 번호로 회원 정보를 조회합니다.
     *
     * @param memberNo 회원 번호
     * @return 회원 정보 응답 DTO
     * @throws NotFoundException 회원이 존재하지 않는 경우
     */
    @Transactional(readOnly = true)
    @Override
    public MemberResponse getMember(long memberNo) {
        log.debug("회원조회 시작! 회원번호 : {}", memberNo);

        Member member = memberRepository.findByMemberNo(memberNo)
                .orElseThrow(() -> new NotFoundException("해당 회원번호의 회원을 찾을 수 없습니다."));

        return MemberResponse.from(member);
    }

    /**
     * 일반 로그인(Local 로그인)을 처리합니다.
     * <p>
     * 소셜 로그인 사용자는 해당 메서드를 통해 로그인할 수 없습니다.
     * </p>
     *
     * @param request 로그인 요청 정보
     * @throws NotFoundException 회원이 존재하지 않는 경우
     * @throws UnauthorizedException 인증 제공자가 LOCAL이 아니거나 비밀번호가 일치하지 않는 경우
     */
    @Transactional(readOnly = true)
    @Override
    public void signInMember(SignInMemberRequest request) {
        log.debug("로그인 시작! 회원 이메일 : {}", request.toString());

        Member member = memberRepository
                .findByMemberEmail(request.memberEmail())
                .orElseThrow(() -> new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다."));

        // 1. 상태 체크 (탈퇴, 잠금 등)
        validateAccountStatusForLogin(member);

        // 2. 제공자 체크 (소셜 계정은 이 로직으로 로그인 불가)
        if (member.getAuthProvider() != AuthProvider.LOCAL) {
            throw new UnauthorizedException("소셜 계정은 해당 로그인을 이용할 수 없습니다.");
        }

        // 3. 비밀번호 매칭
        if (!passwordEncoder.matches(request.memberPwd(), member.getMemberPwd())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원 권한에 따라 회원 정보를 수정합니다.
     *
     * @param memberNo 회원 번호
     * @param role 회원 권한
     * @param request 회원 수정 요청 정보
     * @throws ForbiddenException 수정 권한이 없는 경우
     */
    @Override
    public void updateMember(long memberNo, MemberRole role, UpdateMemberRequest request) {
        switch (role) {
            case USER -> updateUser(memberNo, request);
            case GOV -> updateGov(memberNo, request);
            default -> throw new ForbiddenException("수정 권한이 없습니다.");
        }
    }

    /**
     * 일반 회원의 정보를 수정합니다.
     *
     * @param memberNo 회원 번호
     * @param request 수정 요청 정보
     */
    private void updateUser(long memberNo, UpdateMemberRequest request) {
        Member member = memberRepository
                .findByMemberNoAndMemberStatus(memberNo,  MemberStatus.ACTIVE)
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

    /**
     * 지자체 회원 정보 수정 처리 메서드입니다.
     * <p>
     * 추후 관리자 승인 로직 추가 예정입니다.
     * </p>
     */
    private void updateGov(long memberNo, UpdateMemberRequest request) {
        Member member = memberRepository
                .findByMemberNoAndMemberStatus(memberNo, MemberStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        validateUpdate(request);

        // 지자체 요청 엔티티 구현 후 진행 예정
    }

    /**
     * 이메일 중복 여부를 검증합니다.
     *
     * @param email 회원 이메일
     * @throws ConflictException 이미 존재하는 이메일인 경우
     */
    private void validateDuplicateEmail(String email){
        if(memberRepository.existsByMemberEmail(email)){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }
    }

    /**
     * 생년월일(yyMMdd)을 LocalDate로 변환합니다.
     *
     * @param birth 생년월일 문자열
     * @return 변환된 LocalDate
     * @throws BadRequestException 형식이 잘못되었거나 미래 날짜인 경우
     */
    private LocalDate parseBirth(String birth) {
        if (birth == null || !birth.matches("^\\d{6}$")) {
            throw new BadRequestException("생년월일은 6자리 숫자(yyMMdd)여야 합니다.");
        }

        // 1. 년, 월, 일 분리 파싱 (가독성 증대)
        int yy = Integer.parseInt(birth.substring(0, 2));
        String mmdd = birth.substring(2); // "1231" 형식 유지

        // 2. 연도 계산 (2000년대 vs 1900년대)
        int currentYear = LocalDate.now().getYear();
        int currentYearTwoDigits = currentYear % 100;

        // 예: 현재 26년인데 입력이 30이면 1930년, 20이면 2020년으로 판단
        int fullYear = (yy <= currentYearTwoDigits) ? 2000 + yy : 1900 + yy;

        // 3. 날짜 조합 및 유효성 체크
        try {
            LocalDate birthDate = LocalDate.parse(
                    fullYear + mmdd,
                    DateTimeFormatter.ofPattern("yyyyMMdd")
            );

            // 미래 날짜 확인
            if (birthDate.isAfter(LocalDate.now())) {
                throw new BadRequestException("생년월일이 미래일 수 없습니다.");
            }
            return birthDate;

        } catch (DateTimeParseException e) {
            throw new BadRequestException("유효하지 않은 생년월일입니다.");
        }
    }

    private void validateAccountStatusForLogin(Member member){
        switch(member.getMemberStatus()) {
            case ACTIVE -> {}
            case PENDING -> throw new UnauthorizedException("승인 대기 중인 계정입니다.");
            case LOCKED -> throw new UnauthorizedException("잠긴 계정입니다.");
            case WITHDRAWN -> throw new UnauthorizedException("탈퇴한 계정입니다.");
        }
    }

    /**
     * 회원 수정 요청이 비어있는지 검증합니다.
     *
     * @param request 수정 요청 정보
     * @throws BadRequestException 수정할 항목이 하나도 없는 경우
     */
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