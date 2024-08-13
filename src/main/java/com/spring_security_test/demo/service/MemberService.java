package com.spring_security_test.demo.service;

import com.spring_security_test.demo.entity.Member;
import com.spring_security_test.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void memberInsert(Member member) {
        memberRepository.save(member);
    }

    public Member memberUpdate(Member member) {
        Member memInfo = memberRepository.findByName(member.getName())
                .orElseThrow(() -> { return new IllegalArgumentException("회원찾기 실패"); });

        String rawPassword = member.getPasswd();
        String encPassword = passwordEncoder.encode(rawPassword);

        memInfo.setPasswd(encPassword);
        memInfo.setPhoneNum(member.getPhoneNum());

        memberRepository.saveAndFlush(memInfo);

        return memInfo;
    }
}
