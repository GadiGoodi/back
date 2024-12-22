package com.gagoo.thiscoding.global.security.config;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtLoginFilter;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtUtilImpl;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.utils.HttpServletUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class JwtLoginFilterConfig {
    @Bean
    public JwtLoginFilter jwtLoginFilter(AuthenticationManager authManager,
                                            JwtUtilImpl jwtUtilImpl,
                                            RefreshTokenStore refreshTokenStore,
                                            HttpServletUtils httpServletUtils) {
        return new JwtLoginFilter
                (authManager, jwtUtilImpl, refreshTokenStore, httpServletUtils);
    }
}
