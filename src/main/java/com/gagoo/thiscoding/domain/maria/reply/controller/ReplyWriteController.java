package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class ReplyWriteController {

    private final ReplyService replyService;

    @PostMapping("/{qnaId}/reply")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = HttpStatus.OK)
    public ResponseEntity<Void> create(@PathVariable String qnaId,
                                        @RequestBody ReplyCreate replyCreate) {

        replyService.create(qnaId, replyCreate);

        return ResponseEntity.ok().build();
    }
}
