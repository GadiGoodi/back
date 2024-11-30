package com.gagoo.thiscoding.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정 (서버-클라이언트 간의 메시지 중계 역할)
        config.enableSimpleBroker("/topic"); // 클라이언트 구독 주소 (브로드캐스트 메시지가 전달될 경로)
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 보낼 주소 (클라이언트가 메시지를 서버로 보낼 때 사용할 경로)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드 포인트
        registry.addEndpoint("/ws") // 엔드 포인트
                .setAllowedOriginPatterns("http://localhost:3000") // 허용 도메인 설정
                .withSockJS(); // WebSocket을 지원하지 않는 환경(ex: 구형 브라우저)을 위해, WebSocket 에뮬레이션 기능을 위한 SockJS 사용
    }
}
