package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/{roomId}")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<Boolean> createUserCodeRoom(@PathVariable Long roomId) {
        return ApiResponse
                .created(userCodeRoomService.createUserCodeRoom(roomId),"코드방 참여 생성 성공");
    }
}
