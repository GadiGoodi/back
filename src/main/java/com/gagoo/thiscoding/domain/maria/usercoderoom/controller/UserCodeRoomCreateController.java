package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.CodeRoomCreateResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user-code-rooms")
@RequiredArgsConstructor
public class UserCodeRoomCreateController {
    private final UserCodeRoomService userCodeRoomService;

    // 코드방 참여 생성
    @PostMapping("/{userId}/{roomId}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<Boolean> createUserCodeRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCodeRoomService.createUserCodeRoom(userId, roomId));
    }
}
