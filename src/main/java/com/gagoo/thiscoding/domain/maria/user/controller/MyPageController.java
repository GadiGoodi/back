package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageQnaService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyPageQnA;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageQnaService myPageQnaService;

    @PatchMapping("{userId}")
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfile updateProfile, @PathVariable Long userId) {
        userService.updateImage(userId, updateProfile.getImageUrl());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/qna")
    public ResponseEntity<Page<MyPageQnA>> getMyPageQnA(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(myPageQnaService.getMyPagePostQnA(userId, pageable).map(MyPageQnA::from));

    }
}
