package com.gagoo.thiscoding.global.config;

import com.gagoo.thiscoding.global.security.aop.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor())
                .excludePathPatterns("/swagger-ui/**");
    }

    @Bean
    AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie")
                .allowCredentials(true);
    }
}
