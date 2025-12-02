package com.cu2mber.memberservice.member.domain;

import com.cu2mber.memberservice.member.role.MemberRole;
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private LocalDateTime withdrawalAt;

    public Member(String memberName, String memberEmail, MemberRole memberRole, String memberPwd, String memberPhone, LocalDate memberBirth) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberRole = memberRole;
        this.memberPwd = memberPwd;
        this.memberPhone = memberPhone;
        this.memberBirth = memberBirth;
    }
}
