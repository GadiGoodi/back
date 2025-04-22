package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("email")
    public ApiResponse<Boolean> checkEmail(@RequestParam String email) {
        return ApiResponse
                .ok(userService.checkEmailDuplicate(email),"이메일 확인 성공");
    }

    @GetMapping("nickname")
    public ApiResponse<Boolean> checkNickname(@RequestParam String nickname) {
        return ApiResponse
                .ok(userService.checkNicknameDuplicate(nickname),"닉네임 확인 성공");
    }
}
