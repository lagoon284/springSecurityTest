package com.spring_security_test.demo.repository;

import com.spring_security_test.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String email);

    boolean existsByEmail(String email);

    Optional<Member> findByPhoneNum(String phoneNum);

    boolean existsByPhoneNum(String phoneNum);

}
