package com.cu2mber.memberservice.member.repository;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    Member settingMember() {
        Member member = Member.ofNewUser(
                "회원1",
                "test@test.com",
                "password12!@",
                "01056781234",
                LocalDate.of(1999, 1, 1)
        );

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("existsByMemberEmailAndWithdrawalAtIsNull - 존재하면 true")
    void existsByMemberEmailAndWithdrawalAtIsNull_true() {
        Member member = settingMember();
        entityManager.clear();

        Boolean exists = memberRepository.existsByMemberEmailAndWithdrawalAtIsNull(member.getMemberEmail());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("findByMemberNo - 회원 번호로 조회")
    void findByMemberNo() {
        Member member = settingMember();

        Optional<Member> result = memberRepository.findByMemberNo(member.getMemberNo());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("test@test.com", result.get().getMemberEmail());
    }

    @Test
    @DisplayName("findByMemberEmailAndWithdrawalAtIsNull - 정상 회원 조회")
    void findByMemberEmailAndWithdrawalAtIsNull() {
        settingMember();

        Optional<Member> result = memberRepository.findByMemberEmailAndWithdrawalAtIsNull("test@test.com");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("회원1", result.get().getMemberName());
        Assertions.assertEquals("test@test.com", result.get().getMemberEmail());
        Assertions.assertEquals("password12!@", result.get().getMemberPwd());
        Assertions.assertEquals("01056781234", result.get().getMemberPhone());
    }

    @Test
    @DisplayName("findByMemberNoAndWithdrawalAtIsNull - 정상 회원 조회")
    void findByMemberNoAndWithdrawalAtIsNull() {
        Member member = settingMember();

        Optional<Member> result = memberRepository.findByMemberNoAndWithdrawalAtIsNull(member.getMemberNo());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("회원1", result.get().getMemberName());
        Assertions.assertEquals("test@test.com", result.get().getMemberEmail());
        Assertions.assertEquals("password12!@", result.get().getMemberPwd());
        Assertions.assertEquals("01056781234", result.get().getMemberPhone());
    }

    @Test
    @DisplayName("findByMemberEmailAndAuthProvider - 이메일 + provider로 조회")
    void findByMemberEmailAndAuthProvider() {

        Member member = Member.ofNewSocialUser(
                "회원1",
                "test@test.com",
                "pwd",
                "01056781234",
                LocalDate.of(1999, 1, 1),
                AuthProvider.GOOGLE,
                "google_id"
        );

        memberRepository.save(member);

        Optional<Member> result = memberRepository.findByMemberEmailAndAuthProvider(member.getMemberEmail(), member.getAuthProvider());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("회원1", result.get().getMemberName());
        Assertions.assertEquals("test@test.com", result.get().getMemberEmail());
        Assertions.assertEquals("pwd", result.get().getMemberPwd());
        Assertions.assertEquals("01056781234", result.get().getMemberPhone());
        Assertions.assertEquals(AuthProvider.GOOGLE, result.get().getAuthProvider());
        Assertions.assertEquals("google_id", result.get().getProviderId());
    }
}