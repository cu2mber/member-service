package com.cu2mber.memberservice.member.domain;

import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원 정보를 나타내는 도메인 엔티티입니다.
 * <p>
 * 일반 사용자, 지자체(GOV), 소셜 로그인 사용자를 포함한
 * 모든 회원의 공통 정보를 관리합니다.
 * </p>
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "members")
public class Member {

    /**
     * 지자체 회원의 기본 생년월일 값입니다.
     */
    private static final LocalDate DEFAULT_GOV_BIRTH = LocalDate.of(2000, 1, 1);

    /**
     * 회원 고유 식별자 (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberNo;

    /**
     * 회원 이름
     */
    @Column(length = 50, nullable = false)
    private String memberName;

    /**
     * 회원 이메일 (유니크)
     */
    @Column(length = 255, nullable = false, unique = true)
    private String memberEmail;

    /**
     * 회원 역할 (USER, GOV)
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    /**
     * 회원 비밀번호 (암호화된 값)
     */
    @Column(length = 50, nullable = false)
    private String memberPwd;

    /**
     * 회원 휴대폰 번호 (유니크)
     */
    @Column(length = 20, nullable = false, unique = true)
    private String memberPhone;

    /**
     * 회원 생년월일
     */
    @Column(nullable = false)
    private LocalDate memberBirth;

    /**
     * 인증 제공자 정보 (LOCAL, KAKAO, GOOGLE 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    /**
     * 인증 제공자에서 발급한 사용자 식별자
     */
    @Column(nullable = false)
    private String providerId;

    /**
     * 회원 생성 일시
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 회원 정보 수정 일시
     */
    private LocalDateTime updateAt;

    /**
     * 회원 탈퇴 일시
     */
    private LocalDateTime withdrawalAt;

    /**
     * Member 생성자 (외부 직접 생성 제한)
     */
    private Member(String memberName, String memberEmail, MemberRole memberRole, String memberPwd, String memberPhone, LocalDate memberBirth, AuthProvider authProvider, String providerId) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberRole = memberRole;
        this.memberPwd = memberPwd;
        this.memberPhone = memberPhone;
        this.memberBirth = memberBirth;
        this.authProvider = authProvider;
        this.providerId = providerId;
    }

    /**
     * 일반 사용자(Local) 신규 회원을 생성합니다.
     *
     * @param memberName  회원 이름
     * @param memberEmail 회원 이메일
     * @param memberPwd   암호화된 비밀번호
     * @param memberPhone 회원 휴대폰 번호
     * @param memberBirth 회원 생년월일
     * @return 생성된 Member 엔티티
     */
    public static Member ofNewUser(String memberName, String memberEmail, String memberPwd, String memberPhone, LocalDate memberBirth) {
        return new Member(
                memberName,
                memberEmail,
                MemberRole.USER,
                memberPwd,
                memberPhone,
                memberBirth,
                AuthProvider.LOCAL,
                "LOCAL"
        );
    }

    /**
     * 지자체(GOV) 신규 회원을 생성합니다.
     * <p>
     * 생년월일은 기본값으로 설정됩니다.
     * </p>
     *
     * @param memberName  회원 이름
     * @param memberEmail 회원 이메일
     * @param memberPwd   암호화된 비밀번호
     * @param memberPhone 회원 휴대폰 번호
     * @return 생성된 Member 엔티티
     */
    public static Member ofNewGov(String memberName, String memberEmail, String memberPwd, String memberPhone) {
        return new Member(
                memberName,
                memberEmail,
                MemberRole.GOV,
                memberPwd,
                memberPhone,
                DEFAULT_GOV_BIRTH,
                AuthProvider.LOCAL,
                "LOCAL"
        );
    }

    /**
     * 소셜 로그인 기반 일반 사용자 신규 회원을 생성합니다.
     *
     * @param memberName   회원 이름
     * @param memberEmail  회원 이메일
     * @param memberPwd    암호화된 비밀번호
     * @param memberPhone  회원 휴대폰 번호
     * @param memberBirth  회원 생년월일
     * @param authProvider 인증 제공자
     * @param providerId   제공자 사용자 식별자
     * @return 생성된 Member 엔티티
     */
    public static Member ofNewSocialUser(String memberName, String memberEmail, String memberPwd, String memberPhone, LocalDate memberBirth, AuthProvider authProvider, String providerId) {
        return new Member(
                memberName,
                memberEmail,
                MemberRole.USER,
                memberPwd,
                memberPhone,
                memberBirth,
                authProvider,
                providerId
        );
    }

    /**
     * 일반 사용자 회원 정보를 수정합니다.
     *
     * @param name  변경할 이름
     * @param pwd   변경할 비밀번호
     * @param phone 변경할 휴대폰 번호
     */
    public void updateUser(String name, String pwd, String phone) {
        if(name != null) {
            this.memberName = name;
        }
        if(pwd != null) {
            this.memberPwd = pwd;
        }
        if(phone != null) {
            this.memberPhone = phone;
        }
    }

    /**
     * 지자체(GOV) 회원 정보를 수정합니다.
     *
     * @param name  변경할 이름
     * @param pwd   변경할 비밀번호
     * @param phone 변경할 휴대폰 번호
     */
    public void updateGov(String name, String pwd, String phone) {
        if(name != null) {
            this.memberName = name;
        }
        if(pwd != null) {
            this.memberPwd = pwd;
        }
        if(phone != null) {
            this.memberPhone = phone;
        }
    }
}
