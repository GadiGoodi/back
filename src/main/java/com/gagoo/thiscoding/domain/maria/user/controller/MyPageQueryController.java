package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.response.Top10StatusResponse;
import com.gagoo.thiscoding.domain.maria.user.controller.response.UserProfileResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageQueryController {

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/me/top10")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<Top10StatusResponse> get() {
        return ApiResponse.ok(Top10StatusResponse.from(myPageService.isTop10()), "Top10 여부 조회 성공");
    }

    @GetMapping("/search")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CustomPageDto<UserProfileResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<UserProfileResponse> result = userService.searchByNickname(keyword, pageable)
                .map(UserProfileResponse::from);
        return ApiResponse.ok(CustomPageDto.of(result), "유저 프로필 검색 성공");
    }
}
