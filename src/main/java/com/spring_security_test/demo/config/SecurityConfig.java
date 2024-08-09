package com.spring_security_test.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_security_test.demo.config.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
// @Secured 기능할 수 있게 끔 해주는 어노테이션.
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final PrincipalDetailsService userDetailsService;

    // 스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해). Configure() : 스프링 시큐리티의 모든 기능(인증, 인가 등)을 사용하지 않게 설정
    // requestMatchers() : 특정 요청과 일치하는 url에 대한 액세스 설정
    // ignoring() : requestMatchers에 적힌 url에 대해 인증, 인가를 하지 않음.
    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/h2-console/**")
        );
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성.
    // filterChain() : 특정 Http 요청에 대해 웹 기반 보안 구성. 인증/인가 및 로그아웃을 설정한다.
    //
    // csrf(Cross site Request forgery) : 공격자가 인증된 브라우저에 저장된 쿠키의 세션 정보를 활용하여 웹 서버에 사용자가 의도하지 않은 요청을 전달하는 것. 즉, 정상적인 사용자가 의도치 않은 위조요청을 보내는 것을 의미한다.
    //
    // REST API를 이용한 개발을 진행 할 예정. Rest Api 환경에서는 Session 기반 인증과 다르기 때문에 서버에 인증정보를 보관하지 않고, 권한 요청시 필요한 인증정보(OAuth2, Jwt토큰 등)요청을 포함하기 때문에 굳이 불필요한 csrf 보안을 활성화할 필요가 없음.
    // 따라서 csrf는 disable.
    // HttpBasic(), FormLogin() : Json을 통해 로그인을 진행하는데, 로그인 이후 refresh 토큰이 만료되기 전까지 토큰을 통한 인증을 진행할것 이기 때문에 비활성화.
    //
    // HttpBasic() : Http basic Auth 기반으로 로그인 인증창이 뜬다.
    // authorizeHttpRequests() : 인증, 인가가 필요한 URL 지정
    //
    // role과 authority의 차이점 : role은 prefix로 "ROLE_"을 붙여줌. Authority는 prefix 없이.
    //
    // anyRequest() : requestMatchers에서 지정된 URL외의 요청에 대한 설정
    // authenticated() : 해당 URL에 진입하기 위해서는 인증이 필요함
    // requestMatchers("Url").permitAll() : requestMatchers에서 지정된 url은 인증, 인가 없이도 접근 허용
    // Url에 /**/ 와 같이 ** 사용 : ** 위치에 어떤 값이 들어와도 적용 (와일드 카드)
    // hasAuthority() : 해당 URL에 진입하기 위해서 Authorization(인가, 예를 들면 ADMIN만 진입 가능)이 필요함
    // .hasAuthority(UserRole.ADMIN.name()) 와 같이 사용 가능
    // formLogin() : Form Login 방식 적용
    //
    // loginPage() : 로그인 페이지 URL
    // defaultSuccessURL() : 로그인 성공시 이동할 URL
    // failureURL() : 로그인 실패시 이동할 URL
    // logout() : 로그아웃에 대한 정보
    //
    // invalidateHttpSession() : 로그아웃 이후 전체 세션 삭제 여부
    // sessionManagement() : 세션 생성 및 사용여부에 대한 정책 설정
    //
    // SessionCreationPolicy() : 정책을 설정합니다.
    // SessionCreationPolicy.Stateless : 4가지 정책 중 하나로, 스프링 시큐리티가 생성하지 않고 존재해도 사용하지 않습니다. (JWT와 같이 세션을 사용하지 않는 경우에 사용합니다)
    // 로그인(/login), 회원가입 (/signup), 메인 페이지(/)에 대해서는 인증 없이도 접근할 수 있게 만듦.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/main").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/main").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                        .requestMatchers("/main").authenticated()
                        .anyRequest().permitAll())
//                 폼 로그인은 추후에 적용.
                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
                        .defaultSuccessUrl("/main"))
                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    /* AuthenticationManager 빈 등록 */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {         // AuthenticationManager 등록
        DaoAuthenticationProvider provider = daoAuthenticationProvider();           // DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder());                             // PasswordEncoder로는 PasswordEncoderFactories.createDelegatingPasswordEncoder() 사용
        return new ProviderManager(provider);
    }

//    @Bean
//    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
//        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter= new JsonUsernamePasswordAuthenticationFilter(objectMapper);
//        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        return jsonUsernamePasswordLoginFilter;
//    }
}
