package com.cnco.campusflow.config;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {
    private final AppUserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            AppUserEntity user = userRepository.findByUserId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserId())
                    .password(user.getPassword())
                    .authorities("USER") // 필요하면 역할 추가 가능
                    .build();
        };
    }
}
