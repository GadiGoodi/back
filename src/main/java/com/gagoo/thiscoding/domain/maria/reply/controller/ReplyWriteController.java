package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class ReplyWriteController {

    private final ReplyService replyService;

    @PostMapping("/{qnaId}/reply")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ApiResponse<Void> create(@PathVariable String qnaId, @RequestBody ReplyCreate replyCreate) {

        replyService.create(qnaId, replyCreate);

        return ApiResponse.ok(null, "댓글 작성 성공");
    }

    @DeleteMapping("/{qnaId}/reply/{replyId}")
    public ApiResponse<String> delete(@PathVariable String qnaId, @PathVariable Long replyId) {
        replyService.delete(qnaId, replyId);
        return ApiResponse.ok(null, "댓글 삭제 성공");

    }
}
