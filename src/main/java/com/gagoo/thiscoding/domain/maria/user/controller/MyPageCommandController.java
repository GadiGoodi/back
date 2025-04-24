package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileImageRequest;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageCommandController {

    private final UserService userService;

    @PatchMapping("/me/profile/nickname")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<String> updateProfileNickname(@RequestBody UpdateProfileNicknameRequest request) {
        userService.updateNickname(request);
        return ApiResponse.ok(null,"닉네임 변경 완료");
    }

    @PatchMapping("/me/profile/image")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<String> updateProfileImage(@RequestBody UpdateProfileImageRequest request) {
        userService.updateImage(request);
        return ApiResponse.ok(null,"프로필 사진 변경 완료");
    }
}
