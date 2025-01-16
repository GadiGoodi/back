package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
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
    @ConvertToOneBase
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<CustomPageDto<ReplyList>> getQnAReply(@PathVariable String qnaId, Pageable pageable) {
        return ResponseEntity.ok(replyService.getQnAReply(qnaId, pageable));
    }

}
