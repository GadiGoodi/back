package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
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
    public ResponseEntity<?> createUserCodeRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        userCodeRoomService.createUserCodeRoom(userId, roomId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
