package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CodeSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/broadcast-code") // 클라이언트가 /pub/broadcast로 메시지를 보낼 때 호출
    @SendTo("/sub/codingrooms") // 해당 메시지를 /sub/codingrooms를 구독 중인 모든 클라이언트에게 브로드캐스트
    public void broadcastCode(CodeSocket codeSocket) {
        messagingTemplate.convertAndSend("/sub/codingrooms/" + codeSocket.getRoomId(), codeSocket.getValue());
    }
}
