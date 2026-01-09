package com.cu2mber.memberservice.member.controller;

import com.cu2mber.memberservice.member.dto.request.*;
import com.cu2mber.memberservice.member.dto.response.MemberResponse;
import com.cu2mber.memberservice.member.enums.MemberRole;
import com.cu2mber.memberservice.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관련 요청을 처리하는 REST 컨트롤러입니다.
 * <p>
 * 회원 가입, 로그인, 소셜 로그인 및
 * 본인 정보 조회/수정 기능을 제공합니다.
 * </p>
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    /**
     * 회원 관련 비즈니스 로직을 처리하는 서비스입니다.
     */
    private final MemberService memberService;

    /**
     * 일반 사용자 회원 가입을 처리합니다.
     *
     * @param request 일반 사용자 회원 가입 요청 정보
     * @return 회원 가입 성공 시 201 Created 응답
     */
    @PostMapping("/signUp/user")
    public ResponseEntity<Void> signUpUser(@RequestBody @Valid SignUpUserRequest request){
        memberService.signUpUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 지자체(관리자) 회원 가입을 처리합니다.
     * <p>
     * 현재는 즉시 가입 처리되며,
     * 추후 관리자 승인 로직이 추가될 예정입니다.
     * </p>
     *
     * @param request 지자체 회원 가입 요청 정보
     * @return 회원 가입 성공 시 200 OK 응답
     */
    @PostMapping("/signUp/gov")
    public ResponseEntity<Void> signUpGov(@RequestBody SignUpGovRequest request){
        // 승인 로직 이후 수정할 예정
        memberService.signUpGov(request);

        return ResponseEntity.ok().build();
    }

    /**
     * 소셜 로그인을 수행하거나,
     * 최초 로그인 시 회원 가입을 함께 처리합니다.
     *
     * @param request 소셜 로그인/회원 가입 요청 정보
     * @return 로그인 또는 가입된 회원 정보
     */
    @PostMapping("/social/login")
    public ResponseEntity<MemberResponse> socialLoginOrSignUp(
            @RequestBody @Valid SignUpSocialUserRequest request
    ) {
        MemberResponse response = memberService.socialLoginOrSignUp(request);
        return ResponseEntity
                .ok(response);
    }

    /**
     * 회원 로그인을 처리합니다.
     *
     * @param request 로그인 요청 정보
     * @return 로그인 성공 시 200 OK 응답
     */
    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody @Valid SignInMemberRequest request){
        memberService.signInMember(request);

        return ResponseEntity
                .ok()
                .build();
    }

    /**
     * 로그인한 회원의 정보를 조회합니다.
     *
     * @param memberNo 요청 헤더에 포함된 회원 식별자
     * @return 회원 정보 응답
     */
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(@RequestHeader("X-Member-No") long memberNo){

        return ResponseEntity
                .ok(memberService.getMember(memberNo));
    }

    /**
     * 로그인한 회원의 정보를 조회합니다.
     *
     * @param memberNo 요청 헤더에 포함된 회원 식별자
     * @return 회원 정보 응답
     */
    @PatchMapping("/me")
    public ResponseEntity<Void> updateMyInfo(@RequestHeader("X-Member-No") long memberNo,
                                             @RequestHeader("X-Role") MemberRole role,
                                             @RequestBody @Valid UpdateMemberRequest request) {
        memberService.updateMember(memberNo, role, request);

        return ResponseEntity
                .noContent()
                .build();
    }
}