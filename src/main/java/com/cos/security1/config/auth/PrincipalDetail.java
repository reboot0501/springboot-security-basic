package com.cos.security1.config.auth;
// 시큐리티가 /login 의 요청이 오면 낚아채서 로그인을 진행 시킨다
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줍니다. (시큐리티 session : Security ContextHolder 의미)
// 시큐리티 session을 들어갈 오브젝트는 ==> Authentication 타입의 객체 여야 함
// Authentication 안에 User정보가 있어야 함.
// User 정보 오브젝트 타입은 ==> UserDetail 타입 객체

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

// Security Seesion ==> Authentication ==> UserDetail
public class PrincipalDetail implements UserDetails {
    //
    private User user; //콤포지션

    public PrincipalDetail(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //
        Collection<GrantedAuthority> collect= new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                //
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        //
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        //
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에 1년 동안 회원이 로그인을 하지 않으면 유면 계정으로 하기로 함
        Timestamp loginDate = user.getLoginDate();
        // 현재시간 - 로그인시간(loginDate) => 1년을 초과하면 return false;
        return true;
    }
}
