package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.controller.response.Top10StatusResponse;
import com.gagoo.thiscoding.domain.maria.user.controller.response.UserProfile;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;

    @GetMapping("/me/top10")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<Top10StatusResponse> get() {
        return ApiResponse
                .ok(Top10StatusResponse.from(myPageService.isTop10()),"Top10 여부 조회 성공");
    }

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

    @GetMapping("/search")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CustomPageDto<UserProfile>> search (@RequestParam String keyword, Pageable pageable) {
        Page<UserProfile> result = userService.searchByNickname(keyword, pageable).map(UserProfile::from);
        return ApiResponse
                .ok(CustomPageDto.of(result), "유저 프로필 검색 성공");
    }

}
