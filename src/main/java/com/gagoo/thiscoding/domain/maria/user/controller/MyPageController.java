package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyInfoResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.service.dto.MyInfo;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;

    @GetMapping("/me")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<MyInfoResponse> get() {
        MyInfo myInfo = myPageService.getMyInfo();

        return ResponseEntity
                .ok(MyInfoResponse.from(myInfo));
    }

    @PatchMapping("/me")
    @AuthorizationRequired(value = {Role.USER}, status = OK)
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfile updateProfile) {
        userService.updateImage(updateProfile);
        return ResponseEntity.ok().build();
    }

}
