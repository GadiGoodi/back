package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class ReplyReadController {

    private final ReplyService replyService;

    @GetMapping("/{qnaId}/reply")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<ReplyList>> getQnAReply(@PathVariable String qnaId, Pageable pageable) {
        return ApiResponse.ok(replyService.getQnAReply(qnaId, pageable),"댓글 목록 조회 완료");
    }

    @GetMapping("/{qnaId}/reply/{parentId}")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<ReplyList>> getReplies(@PathVariable String qnaId,
                                                               @PathVariable Long parentId,Pageable pageable){
        return ApiResponse.ok(replyService.getReplies(qnaId,parentId, pageable),"대댓글 목록 조회 완료");
    }

}
