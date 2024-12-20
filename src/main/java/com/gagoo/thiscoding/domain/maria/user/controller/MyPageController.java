package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageQnaService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyInfoResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyPageQnA;
import com.gagoo.thiscoding.domain.maria.user.service.dto.MyInfo;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;
    private final MyPageQnaService myPageQnaService;

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

    @GetMapping("/{userId}/qna")
    public ResponseEntity<Page<MyPageQnA>> getMyPageQnA(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(myPageQnaService.getMyPagePostQnA(userId, pageable).map(MyPageQnA::from));

    }
}
