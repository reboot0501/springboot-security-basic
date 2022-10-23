package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다!!!
@Slf4j
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // localhost:8080/
    // localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // mustache 기본폴더 : src/main/resources/
        // 뷰리졸버 설정 : templates (prdfix), .mustache (suffix) --> 생략가능
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        //
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        //
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        //
        return "manager";
    }
    // 스프링시큐리티가 해당 주소를 낚아채버림 - SecurityConfig 파일 생성후 작동안함.
    @GetMapping("/loginForm")
    public String loginForm() {
        //
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        //
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        //
        log.info(user.toString());

        user.setRole("ROLE_USER");
        //회원가입 잘됨 비번 : 1234 => 시큐리티로 로그인을 할 수 없음, 이유는 패스워드가 암호화가 안되었기 때문에 !!!!
        String rawPassword = user.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encodePassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    // 시큐리티 설정(SecurityConfig) @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secure 어노테이션 활성화, preAuthorize 어노테이션 활성화
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    // 시큐리티 설정(SecurityConfig) @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secure 어노테이션 활성화, preAuthorize 어노테이션 활성화
    // data Method 실행 직전에 실행
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "데이터정보";
    }


}
