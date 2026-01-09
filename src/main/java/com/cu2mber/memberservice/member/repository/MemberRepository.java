package com.cu2mber.memberservice.member.repository;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByMemberEmailAndWithdrawalAtIsNull(String memberEmail);

    Optional<Member> findByMemberNo(long memberNo);

    Optional<Member> findByMemberEmailAndWithdrawalAtIsNull(String memberEmail);

    Optional<Member> findByMemberNoAndWithdrawalAtIsNull(long memberNo);

    Optional<Member> findByMemberEmailAndAuthProvider(String memberEmail, AuthProvider provider);
}
