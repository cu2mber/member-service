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

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp/user")
    public ResponseEntity<Void> signUpUser(@RequestBody @Valid SignUpUserRequest request){
        memberService.signUpUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/signUp/gov")
    public ResponseEntity<Void> signUpGov(@RequestBody SignUpGovRequest request){
        // 승인 로직 이후 수정할 예정
        memberService.signUpGov(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/social/login")
    public ResponseEntity<MemberResponse> socialLoginOrSignUp(
            @RequestBody @Valid SignUpSocialUserRequest request
    ) {
        MemberResponse response = memberService.socialLoginOrSignUp(request);
        return ResponseEntity
                .ok(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody @Valid SignInMemberRequest request){
        memberService.signInMember(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(@RequestHeader("X-Member-No") long memberNo){

        return ResponseEntity
                .ok(memberService.getMember(memberNo));
    }

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