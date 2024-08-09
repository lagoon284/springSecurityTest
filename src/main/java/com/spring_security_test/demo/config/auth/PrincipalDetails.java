package com.spring_security_test.demo.config.auth;

import com.spring_security_test.demo.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//시큐리티가 /login 주소 요청이 오면 가로채서 로그인을 진행해서 컨트롤러에 /login이 필요없음
//로그인 진행이 완료가 되면 시큐리티 session을 생성 (security contextHolder)
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야됨.
//User 오브젝트 타입=> UserDetail타입 객체

//security Session =-> Authentication=>UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails {

    private Map<String, Object> attrbutes;
    private Member member;

    // 일반 로그인.
    public PrincipalDetails(Member member) { this.member = member; }

    public Member getMember() { return member; }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
        collet.add(() -> { return member.getRole();});

        return collet;
    }

    @Override
    public String getPassword() {
        return member.getPasswd();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    //계정이 만료됬는지 확인 true가 만료안됨
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겼는지 true가 잠기지않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료여부  true가 만료안됨
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화 되었는지
    @Override
    public boolean isEnabled() {
        return true;
    }
}
