package com.gagoo.thiscoding.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { // CORS 규칙 정의 (다른 도메인(프론트)에서 리소스 접근 허용 설정)
        registry.addMapping("/**")  // 백엔드의 모든 경로(엔드포인트)에 대해 CORS 규칙 적용(허용)
                .allowedOrigins("http://localhost:3000") // 프론트엔드의 URL port에서 오는 요청 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // 허용할 HTTP 메서드
    }
}