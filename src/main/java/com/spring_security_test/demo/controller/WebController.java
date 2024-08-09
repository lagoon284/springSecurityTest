package com.spring_security_test.demo.controller;

import com.spring_security_test.demo.config.auth.PrincipalDetails;
import com.spring_security_test.demo.entity.Member;
import com.spring_security_test.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @RequestMapping(value = "/")
    public String indexPage() {
        return "index";
    }

    // 로그인 성공 페이지
    @RequestMapping(value = "/main")
    public String basicPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        model.addAttribute("member", principalDetails.getMember());

        return "index2";
    }

    @RequestMapping(value = "/signupPage")
    public String signupPage() {
        return "signup";
    }

    // 회원가입 페이지
    @RequestMapping(value = "/signup", method = POST)
    public void signup(Member member) {

        String pw = member.getPasswd();
        String prefixPw = "{noop}" + pw;

        member.setPasswd(prefixPw);

        memberService.memberInsert(member);
    }
}
