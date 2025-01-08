package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class ReplyReadController {

    private final ReplyService replyService;

    @GetMapping("/{qnaId}/reply")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<CustomPageDto> getQnAReply(@PathVariable String qnaId, Pageable pageable) {

        return ResponseEntity.ok(replyService.getQnAReply(qnaId, pageable));
    }


    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> delete(@PathVariable Long replyId) {
        replyService.delete(replyId);
        return ResponseEntity
                .ok()
                .body("댓글이 삭제되었습니다.");
    }
}
