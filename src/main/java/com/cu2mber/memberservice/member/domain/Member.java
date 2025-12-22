package com.cu2mber.memberservice.member.domain;

import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "members")
public class Member {

    private static final LocalDate DEFAULT_GOV_BIRTH = LocalDate.of(2000, 1, 1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberNo;

    @Column(length = 50, nullable = false)
    private String memberName;

    @Column(length = 255, nullable = false, unique = true)
    private String memberEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(length = 50, nullable = false)
    private String memberPwd;

    @Column(length = 20, nullable = false, unique = true)
    private String memberPhone;

    @Column(nullable = false)
    private LocalDate memberBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(nullable = false)
    private String providerId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private LocalDateTime withdrawalAt;

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
}
