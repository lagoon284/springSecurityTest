package com.spring_security_test.demo.controller;

import com.spring_security_test.demo.config.auth.PrincipalDetails;
import com.spring_security_test.demo.entity.Member;
import com.spring_security_test.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class WebController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/")
    public String indexPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("loginName", principalDetails.getMember().getName());
        }

        return "index";
    }

    // 로그인 성공 페이지
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/main")
    public String basicPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("member", principalDetails.getMember());
        }

        return "index2";
    }

    @RequestMapping(value = "/signupPage")
    public String signupPage() {

        return "signup";
    }

    // 회원가입 페이지
    @RequestMapping(value = "/signup", method = POST)
    public void signup(Member member) {

        member.setPasswd("{noop}" + passwordEncoder.encode(member.getPasswd()));

        memberService.memberInsert(member);
    }
}
