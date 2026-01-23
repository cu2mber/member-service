package com.cu2mber.memberservice.member.service;

import com.cu2mber.memberservice.common.exception.BadRequestException;
import com.cu2mber.memberservice.common.exception.ConflictException;
import com.cu2mber.memberservice.common.exception.UnauthorizedException;
import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.dto.request.SignInMemberRequest;
import com.cu2mber.memberservice.member.dto.request.SignUpSocialUserRequest;
import com.cu2mber.memberservice.member.dto.request.SignUpUserRequest;
import com.cu2mber.memberservice.member.dto.request.UpdateMemberRequest;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import com.cu2mber.memberservice.member.repository.MemberRepository;
import com.cu2mber.memberservice.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MemberServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("일반 회원가입 테스트 - 성공")
    void signUpUser_success() {
        SignUpUserRequest request = new SignUpUserRequest(
                "test@test.com",
                "일반 회원",
                "password123!@#",
                "password123!@#",
                "01012345678",
                "990101"
        );

        when(memberRepository.existsByMemberEmailAndWithdrawalAtIsNull(anyString())).thenReturn(false);

        memberService.signUpUser(request);

        verify(memberRepository, times(1)).existsByMemberEmailAndWithdrawalAtIsNull(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("일반 회원가입 실패 - 이메일 중복")
    void signUpUser_fail() {
        SignUpUserRequest request = new SignUpUserRequest(
                "test@test.com",
                "일반 회원",
                "password1!",
                "password1!",
                "01012345678",
                "990101"
        );

        when(memberRepository.existsByMemberEmailAndWithdrawalAtIsNull(anyString())).thenReturn(true);

        Assertions.assertThrows(ConflictException.class, () -> memberService.signUpUser(request));

        verify(memberRepository, times(1)).existsByMemberEmailAndWithdrawalAtIsNull(anyString());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("일반 회원 로그인 - 성공")
    void signInMember_success() {
        SignInMemberRequest request = new SignInMemberRequest(
                "test@test.com",
                "password12!@"
        );

        Member member = mock(Member.class);

        when(memberRepository.findByMemberEmailAndMemberStatus_Active(request.memberEmail())).thenReturn(Optional.of(member));
        when(member.getAuthProvider()).thenReturn(AuthProvider.LOCAL);
        when(member.getMemberPwd()).thenReturn("encoded-password");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Assertions.assertDoesNotThrow(
                () -> memberService.signInMember(request)
        );

        verify(memberRepository, times(1)).findByMemberEmailAndMemberStatus_Active(request.memberEmail());

        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("소셜 계정으로 일반 로그인 시도")
    void signInMember_socialUser_throwUnauthorized() {
        SignInMemberRequest request = new SignInMemberRequest(
                "test@test.com",
                "password123!@#"
        );

        Member member = mock(Member.class);

        when(member.getAuthProvider()).thenReturn(AuthProvider.GOOGLE);
        when(memberRepository.findByMemberEmailAndMemberStatus_Active(anyString())).thenReturn(Optional.of(member));

        Assertions.assertThrows(UnauthorizedException.class, () -> memberService.signInMember(request));

        verify(memberRepository, times(1)).findByMemberEmailAndMemberStatus_Active(anyString());
        verify(member, times(1)).getAuthProvider();
    }

    @Test
    @DisplayName("소셜 로그인 - 기존 회원이면 조회 후 반환")
    void socialLogin_ExistingUser() {
        Member member = mock(Member.class);

        when(memberRepository.findByMemberEmailAndMemberStatus_Active(anyString())).thenReturn(Optional.of(member));
        when(member.getMemberNo()).thenReturn(1L);
        when(member.getMemberEmail()).thenReturn("social@test.com");
        when(member.getAuthProvider()).thenReturn(AuthProvider.GOOGLE);

        MemberResponse response = mock(MemberResponse.class);

        Assertions.assertNotNull(response);

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("소셜 로그인 - 신규 회원이면 저장 후 반환")
    void socialLogin_NewUser() {
        SignUpSocialUserRequest request = new SignUpSocialUserRequest(
                "social@test.com",
                "신규 소셜",
                "01012344321",
                "000303",
                AuthProvider.GOOGLE,
                "google_id"
        );

        when(memberRepository.findByMemberEmailAndAuthProvider(
                request.memberEmail(),
                request.provider()
        )).thenReturn(Optional.empty());

        Member savedMember = Member.ofNewSocialUser(
                request.memberName(),
                request.memberEmail(),
                null,
                request.memberPhone(),
                LocalDate.of(2000, 3, 3),
                request.provider(),
                request.providerId()
        );

        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        MemberResponse response = memberService.socialLoginOrSignUp(request);

        assertNotNull(response);
        assertEquals("social@test.com", response.memberEmail());

        verify(memberRepository, times(1)).findByMemberEmailAndAuthProvider(request.memberEmail(), request.provider());

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 수정 - 수정할 값 없음")
    void updateUser_noChanges_throwBadRequest() {

        long memberNo = 1L;

        UpdateMemberRequest request = new UpdateMemberRequest(
                null,
                null,
                null,
                null
        );

        Member member = mock(Member.class);

        when(memberRepository.findByMemberNoAndMemberStatus_Active(memberNo)).thenReturn(Optional.of(member));

        Assertions.assertThrows(BadRequestException.class,
                () -> memberService.updateMember(memberNo, MemberRole.USER, request)
        );

        verify(memberRepository, times(1)).findByMemberNoAndMemberStatus_Active(memberNo);
        verify(member, never()).updateUser(any(), any(), any());
    }

    @Test
    @DisplayName("회원 수정 - 비밀번호 불일치")
    void updateUser_passwordMismatch_throwBadRequest() {

        long memberNo = 1L;

        UpdateMemberRequest request = new UpdateMemberRequest(
                "수정이름",
                "new-password",
                "different-password",
                "01011112222"
        );

        Member member = mock(Member.class);

        when(memberRepository.findByMemberNoAndMemberStatus_Active(memberNo)).thenReturn(Optional.of(member));

        // when & then
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                () -> memberService.updateMember(memberNo, MemberRole.USER, request)
        );

        assertEquals("새 비밀번호가 일치하지 않습니다.", exception.getMessage());

        verify(passwordEncoder, never()).encode(any());
        verify(member, never()).updateUser(any(), any(), any());
    }

    @Test
    @DisplayName("생년월일 파싱 테스트 - 잘못된 날짜")
    void signUpUser_invalidBirth_throwBadRequest() {
        // given
        SignUpUserRequest request = new SignUpUserRequest(
                "test@test.com",
                "홍길동",
                "Password!1",
                "Password!1",
                "01012345678",
                "991332"
        );

        when(memberRepository.existsByMemberEmailAndWithdrawalAtIsNull(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPwd");

        BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                () -> memberService.signUpUser(request)
        );
        Assertions.assertEquals("유효하지 않은 생년월일입니다.", exception.getMessage());
    }
}