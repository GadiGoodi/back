package com.gagoo.thiscoding.global.security.config;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.CustomUserDetailsService;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtFilter;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    public JwtFilter jwtFilter(CustomUserDetailsService customUserDetailsService, JwtUtilImpl jwtUtilImpl) {
        return new JwtFilter(customUserDetailsService, jwtUtilImpl);
    }
}