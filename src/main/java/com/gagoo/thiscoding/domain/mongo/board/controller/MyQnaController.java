package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageAnswer;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageQnA;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
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

    @AuthorizationRequired(value = {Role.USER,Role.ADMIN}, status = OK)
    @GetMapping("/qna")
    @ConvertToOneBase
    public ResponseEntity<CustomPageDto<MyPageQnA>> getMyPageQnA(Pageable pageable) {

        Page<MyPageQnA> qnaResult = boardService.getMyPagePostQnA(pageable).map(MyPageQnA::from);
        return ResponseEntity.ok(CustomPageDto.of(qnaResult));

    }

    @AuthorizationRequired(value = {Role.USER,Role.ADMIN}, status = OK)
    @GetMapping("/answer")
    @ConvertToOneBase
    public ResponseEntity<CustomPageDto<MyPageAnswer>> getMyPageAnswer(Pageable pageable) {
        Page<MyPageAnswer> answerResult = boardService.getMyPagePostAnswer(pageable).map(MyPageAnswer::from);
        return ResponseEntity.ok(CustomPageDto.of(answerResult));

    }
}
