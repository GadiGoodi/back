package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class BoardCommandController {

    private final BoardService boardService;

    @PostMapping
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ApiResponse<Void> create(@RequestBody BoardCreate boardCreate) {
        boardService.create(boardCreate);

        return ApiResponse.created(null, "QnA 질문 작성 성공");
    }

    @PostMapping("/{qnaId}/answer")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ApiResponse<Void> writeAnswer(@PathVariable String qnaId, @RequestBody BoardAnswer boardAnswer) {
        boardService.writeAnswer(qnaId, boardAnswer);

        return ApiResponse.created(null, "QnA 답변 작성 성공");
    }

    @PostMapping("/{qnaId}/adopt")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ApiResponse<String> adoptAnswer(@PathVariable String qnaId) {
        boardService.adoptAnswer(qnaId);

        return ApiResponse
                .ok(null, "답변이 채택되었습니다.");
    }
}
