package com.cu2mber.memberservice.member.repository;

import com.cu2mber.memberservice.member.domain.Member;
import com.cu2mber.memberservice.member.enums.AuthProvider;
import com.cu2mber.memberservice.member.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원(Member) 엔티티에 대한 데이터 접근을 담당하는 Repository 인터페이스입니다.
 * <p>
 * 탈퇴 회원(withdrawalAt != null)을 제외한 조회 메서드를 중심으로 제공하며,
 * 이메일, 회원 번호, 인증 제공자 기준의 조회를 지원합니다.
 * </p>
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 탈퇴하지 않은 회원 중 이메일이 존재하는지 확인합니다.
     *
     * @param memberEmail 회원 이메일
     * @return 존재 여부
     */
    Boolean existsByMemberEmail(String memberEmail);

    Optional<Member> findByMemberEmail(String memberEmail);

    /**
     * 회원 번호로 회원을 조회합니다.
     *
     * @param memberNo 회원 번호
     * @return 회원 정보
     */
    Optional<Member> findByMemberNo(long memberNo);

    /**
     * 탈퇴하지 않은 회원을 이메일로 조회합니다.
     *
     * @param memberEmail 회원 이메일
     * @return 회원 정보
     */
    Optional<Member> findByMemberEmailAndMemberStatus(String memberEmail, MemberStatus status);

    /**
     * 탈퇴하지 않은 회원을 회원 번호로 조회합니다.
     *
     * @param memberNo 회원 번호
     * @return 회원 정보
     */
    Optional<Member> findByMemberNoAndMemberStatus(long memberNo, MemberStatus status);

    /**
     * 인증 제공자 기준으로 회원을 조회합니다.
     * <p>
     * 소셜 로그인 사용자를 식별하기 위해 사용됩니다.
     * </p>
     *
     * @param memberEmail 회원 이메일
     * @param provider 인증 제공자
     * @return 회원 정보
     */
    Optional<Member> findByMemberEmailAndAuthProvider(String memberEmail, AuthProvider provider);
}