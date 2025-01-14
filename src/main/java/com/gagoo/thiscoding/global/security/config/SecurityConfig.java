package com.gagoo.thiscoding.global.security.config;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtFilter;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtLoginFilter;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.JwtUtilImpl;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.security.JwtProperties;
import com.gagoo.thiscoding.global.utils.HttpServletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationConfiguration authenticationConfig;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtUtilImpl jwtUtilImpl;
    private final JwtProperties jwtProperties;
    private final HttpServletUtils httpServletUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(auth -> auth.disable())
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/**")
                                ).permitAll()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterAt(new JwtLoginFilter(
                        authenticationManager(authenticationConfig), jwtUtilImpl, refreshTokenStore, httpServletUtils, jwtProperties),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Set-Cookie");
        configuration.setExposedHeaders(getExposedHeaders());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> getExposedHeaders() {
        return Arrays.asList(
                "Content-Type",
                AUTHORIZATION, SET_COOKIE,
                ACCESS_CONTROL_ALLOW_HEADERS,
                ACCESS_CONTROL_ALLOW_ORIGIN,
                ACCESS_CONTROL_ALLOW_METHODS,
                ACCESS_CONTROL_EXPOSE_HEADERS
        );
    }
}
