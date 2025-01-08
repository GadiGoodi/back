package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageQnA;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyQnaController {

    private final BoardService boardService;

    @AuthorizationRequired(value = {Role.USER}, status = OK)
    @GetMapping("/qna")
    public ResponseEntity<Page<MyPageQnA>> getMyPageQnA(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(boardService.getMyPagePostQnA(pageable).map(MyPageQnA::from));

    }
}
