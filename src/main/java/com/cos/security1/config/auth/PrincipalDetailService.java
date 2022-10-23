package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정(Security Config)에서 loginProcessingUrl("/login") 으로 걸어 놓았기 때문에
// "/login" 여청이 오면 자동으로 UserDetailService 타입으로 Ioc되어 있는 loadUserByUsername 함수가 실행 됨
@Service
@Slf4j
public class PrincipalDetailService implements UserDetailsService {
    //
    @Autowired
    private UserRepository userRepository;

    /**
      * 반드시 loginForm의 username과 반드시 같아야 함
     *  만약 다른 이름 (username2)으로 하고 싶다면 시큐리티 설정(Security Config)에서 .usernameParameter(username2)로 설정해야 동작함
     *  시큐리티 session(Authentication(내부 UserDetail) )
     */
    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        //
        log.info("---------------------------> username : " + username);
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {
            return new PrincipalDetail(userEntity);
        }
        return null;
    }


}
