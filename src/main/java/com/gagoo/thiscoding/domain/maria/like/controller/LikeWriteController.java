package com.gagoo.thiscoding.domain.maria.like.controller;

import com.gagoo.thiscoding.domain.maria.like.controller.port.LikeService;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class LikeWriteController {

    private final LikeService likeService;

    // 답변 추천
    @PostMapping("/{qnaId}/like")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<String> likeAnswer(@PathVariable String qnaId) {
        likeService.likeAnswer(qnaId);

        return ApiResponse.ok(null,"답변 추천이 완료되었습니다.");
    }

    // 답변 추천 취소
    @DeleteMapping("/{qnaId}/cancel-like")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<String> cancelAnswerLike(@PathVariable String qnaId) {
        likeService.cancelAnswerLike(qnaId);

        return ApiResponse.ok(null,"답변 추천을 취소하였습니다.");
    }
}
