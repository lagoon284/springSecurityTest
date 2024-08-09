package com.spring_security_test.demo.config.auth;

import com.spring_security_test.demo.entity.Member;
import com.spring_security_test.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;

    //username이 로그인의 name의 username과 같아야됨 바꾸려면 SecurityConfig에서 바꺼야됨
    //시큐리티 session = Authentication(내부에 UserDetails가 들어가고)
    //시큐리티 session(Authentication(내부에 UserDetails가 들어가고))내부에 들어감
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException(username));
        return new PrincipalDetails(member);

        /*User.builder().username(member.getName())
                .password(member.getPasswd())
                .roles(member.getRole())
                .build();*/
    }
}
