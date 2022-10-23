package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secure 어노테이션 활성화, preAuthorize, postAuthorize(자주 없없음) 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@Bean 부여하면 해당 메소드의 리터되는 오브젝트를 Ioc로 등록
    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증이 필요
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // Admin이나 Manager 권한을 가지는
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // Admin 만
                .anyRequest().permitAll() // 위의 3개 URL 이 아인 다른 요청은 다 권한이 허용
                .and()// and 아래 부터는 권란이 없은 요청은 무조건 로그인 화면이 열릴 수 있도록 처리
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // Controller에 (/login) 을 안만들어도 된다. /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
                .defaultSuccessUrl("/") // main 페이지로 이동
                ;

    }
}
