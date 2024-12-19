package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageQnaService;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyPageQnA;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final MyPageQnaService myPageQnaService;

    @GetMapping("/{userId}/qna")
    public ResponseEntity<Page<MyPageQnA>> getMyPageQnA(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(myPageQnaService.getMyPagePostQnA(userId, pageable).map(MyPageQnA::from));

    }
}
