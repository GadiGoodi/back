package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class BoardWriteController {

    private final BoardService boardService;

    @PostMapping
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<Void> create(@RequestBody BoardCreate boardCreate) {
        boardService.create(boardCreate);

        return ResponseEntity.created(null).build();
    }

    @PostMapping("/{qnaId}/answer")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<Void> writeAnswer(@PathVariable String qnaId, @RequestBody BoardAnswer boardAnswer) {
        boardService.writeAnswer(qnaId, boardAnswer);

        return ResponseEntity.created(null).build();
    }

    @PostMapping("/{qnaId}/adopt")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<String> adoptAnswer(@PathVariable String qnaId) {
        boardService.adoptAnswer(qnaId);

        return ResponseEntity
                .ok()
                .body("답변이 채택되었습니다.");
    }
}
