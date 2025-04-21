package com.gagoo.thiscoding.global.security.config;

import com.gagoo.thiscoding.domain.auth.infrastructure.oauth.OAuth2UserServiceHandler;
import com.gagoo.thiscoding.domain.auth.infrastructure.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gagoo.thiscoding.global.security.infrastructure.filter.AuthExceptionFilter;
import com.gagoo.thiscoding.global.security.infrastructure.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    private final OAuth2AuthenticationSuccessHandler oAuthSuccessHandler;
    private final OAuth2UserServiceHandler oAuthServiceHandler;
    private final JwtFilter jwtFilter;
    private final AuthExceptionFilter authExceptionFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

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
                                .requestMatchers(PUBLIC_NON_GET_URIS).permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_GET_URIS).permitAll()
                                .anyRequest().authenticated()
                );

        http
                .oauth2Login(configure -> {
                    configure.authorizationEndpoint(endpoint ->
                                    endpoint.baseUri("/oauth2/authorization")
                            )
                            .userInfoEndpoint(customizer ->
                                    customizer.userService(oAuthServiceHandler)
                            )
                            .successHandler(oAuthSuccessHandler);
                });

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authExceptionFilter, JwtFilter.class);


        return http.build();
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

    private static final String[] PUBLIC_NON_GET_URIS = {
            "/api/auth/sign-up",
            "/login/oauth2/**",
            "/oauth2/authorization/**",
            "/api/auth/login",
            "/api/auth/reset-password"
    };

    private static final String[] PUBLIC_GET_URIS = {
            "/api/auth/email",
            "/api/auth/nickname",
            "/api/admin/notices",
            "/api/admin/notices/*",
            "/api/qna/*/reply",
            "/api/qna/*/reply/*",
            "/api/qna",
            "/api/qna/search",
            "/api/qna/*",
            "/api/qna/*/answer"
    };
}
