package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.UserCodeRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-code-rooms")
@RequiredArgsConstructor
public class UserCodeRoomCreateController {
    private final UserCodeRoomService userCodeRoomService;

    // 코드방 참여 생성
    @PostMapping("/{userId}/{roomId}")
    public ResponseEntity<UserCodeRoomResponse> createUserCodeRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCodeRoomService.createUserCodeRoom(userId, roomId));
    }
}
