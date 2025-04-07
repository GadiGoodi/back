package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class CodeSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    // 마지막 메시지 저장 변수
    // <roomId + codeId, 마지막 메시지 값>
    private final ConcurrentHashMap<String, String> lastMessages = new ConcurrentHashMap<>();

    @MessageMapping("/broadcast-code") // 클라이언트가 /pub/broadcast로 메시지를 보낼 때 호출
    @SendTo("/sub/codingrooms") // 해당 메시지를 /sub/codingrooms를 구독 중인 모든 클라이언트에게 브로드캐스트
    public void broadcastCode(CodeSocket codeSocket) {
        // 마지막 메시지 값 저장
        lastMessages.put(codeSocket.getRoomId() + codeSocket.getCodeId(), codeSocket.getValue());

        // 해당 경로를 구독 중인 모든 클라이언트에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/codingrooms/" + codeSocket.getRoomId() + "/" + codeSocket.getCodeId(), codeSocket.getValue());
    }

    // 새로운 클라이언트가 구독 시, 마지막 메시지 전송
    @MessageMapping("/codingrooms/join/{roomId}/{codeId}")
    public void sendLastMessage(@DestinationVariable Long roomId, @DestinationVariable String codeId) {
        String lastMessage = lastMessages.get(roomId + codeId);

        if (lastMessage != null) {
            messagingTemplate.convertAndSend("/sub/codingrooms/" + roomId + "/" + codeId, lastMessage);
        }
    }
}
