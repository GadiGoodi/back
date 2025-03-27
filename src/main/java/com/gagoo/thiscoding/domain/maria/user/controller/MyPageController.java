package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.controller.response.Top10StatusResponse;
import com.gagoo.thiscoding.domain.maria.user.controller.response.UserProfile;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;

    @GetMapping("/me/top10")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<Top10StatusResponse> get() {
        return ResponseEntity
                .ok(Top10StatusResponse.from(myPageService.isTop10()));
    }

    @PatchMapping("/me/profile/nickname")
    @AuthorizationRequired(value = {Role.USER}, status = OK)
    public ResponseEntity<String> updateProfileNickname(@RequestBody UpdateProfileNicknameRequest request) {
        userService.updateNickname(request);
        return ResponseEntity.ok().body("닉네임 변경 완료");
    }

    @PatchMapping("/me/profile/image")
    @AuthorizationRequired(value = {Role.USER}, status = OK)
    public ResponseEntity<String> updateProfileImage(@RequestBody UpdateProfileImageRequest request) {
        userService.updateImage(request);
        return ResponseEntity.ok().body("프로필 사진 변경 완료");
    }

    @GetMapping("/search")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CustomPageDto<UserProfile>> search (@RequestParam String keyword, Pageable pageable) {
        Page<UserProfile> result = userService.searchByNickname(keyword, pageable).map(UserProfile::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(result));
    }

}
