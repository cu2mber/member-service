package com.cu2mber.memberservice.member.repository;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByMemberEmail(String memberEmail);

    Optional<Member> findByMemberEmail(String memberEmail);

    Optional<Member> findByMemberPhone(String memberPhone);

    Page<Member> findAllByMemberRole(MemberRole memberRole, Pageable pageable);
}
