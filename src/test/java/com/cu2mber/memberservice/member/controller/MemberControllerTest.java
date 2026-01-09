package com.cu2mber.memberservice.member.controller;

import com.cu2mber.memberservice.member.dto.request.SignInMemberRequest;
import com.cu2mber.memberservice.member.dto.request.SignUpSocialUserRequest;
import com.cu2mber.memberservice.member.dto.request.SignUpUserRequest;
import com.cu2mber.memberservice.member.dto.request.UpdateMemberRequest;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import com.cu2mber.memberservice.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 요청 - 201 반환")
    void singUpUser() throws Exception {
        SignUpUserRequest request = new SignUpUserRequest(
                "test@test.com",
                "일반회원",
                "password123!@#",
                "password123!@#",
                "01011223344",
                "001010"
        );

        mockMvc.perform(post("/members/signUp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(memberService, times(1)).signUpUser(any(SignUpUserRequest.class));
    }

    @Test
    @DisplayName("소셜 로그인 또는 회원가입 - 성공 시 MemberResponse 반환")
    void socialLoginOrSignUp_success() throws Exception {
        SignUpSocialUserRequest request = new SignUpSocialUserRequest(
                "social@test.com",
                "소셜유저",
                "01011112222",
                "000303",
                AuthProvider.GOOGLE,
                "google-id"
        );

        MemberResponse response = new MemberResponse(
                1L,
                "social@test.com",
                "소셜유저",
                MemberRole.USER,
                "01011112222",
                LocalDate.of(2000,3,3),
                AuthProvider.GOOGLE,
                LocalDateTime.now()
        );

        when(memberService.socialLoginOrSignUp(any()))
                .thenReturn(response);

        mockMvc.perform(post("/members/social/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberNo").value(1L))
                .andExpect(jsonPath("$.memberName").value("소셜유저"))
                .andExpect(jsonPath("$.memberRole").value("USER"))
                .andExpect(jsonPath("$.authProvider").value("GOOGLE"))
                .andExpect(jsonPath("$.createdAt").exists());

        verify(memberService).socialLoginOrSignUp(any());
    }

    @Test
    @DisplayName("로그인 성공 - 200 OK")
    void signIn_success() throws Exception {
        SignInMemberRequest request = new SignInMemberRequest(
                "user@test.com",
                "password123!"
        );

        mockMvc.perform(post("/members/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(memberService).signInMember(any());
    }

    @Test
    @DisplayName("내 정보 조회 - 200 OK")
    void getMyInfo_success() throws Exception {
        MemberResponse response = new MemberResponse(
                1L,
                "test1@test.com",
                "테스트유저",
                MemberRole.USER,
                "01011112222",
                LocalDate.of(2000,3,3),
                AuthProvider.GOOGLE,
                LocalDateTime.now()
        );

        when(memberService.getMember(1L))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/members/me")
                        .header("X-Member-No", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberEmail").value("test1@test.com"));

        verify(memberService).getMember(1L);
    }

    @Test
    @DisplayName("내 정보 수정 - 성공 시 204 No Content")
    void updateMyInfo_success() throws Exception {
        UpdateMemberRequest request = new UpdateMemberRequest(
                "테스트2",
                "newpassword123",
                "newpassword123",
                "01088887777"
        );

        mockMvc.perform(patch("/members/me")
                        .header("X-Member-No", 1L)
                        .header("X-Role", MemberRole.USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(memberService)
                .updateMember(eq(1L), eq(MemberRole.USER), any());
    }
}