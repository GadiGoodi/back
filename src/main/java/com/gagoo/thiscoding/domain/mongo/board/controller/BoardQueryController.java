package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardQueryService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.AnswerResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.QnaResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.KeywordSearchResponse;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.Answer;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnA;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.VisitorIdProvider;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class BoardQueryController {

    private final BoardQueryService boardQueryService;
    private final VisitorIdProvider visitorIdProvider;

    @GetMapping("/{qnaId}")
    public ApiResponse<QnaResponse> get(@PathVariable String qnaId, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[컨트롤러 진입] qnaId = " + qnaId);
        System.out.println("[요청 URI] = " + request.getRequestURI());
        String visitorId = visitorIdProvider.getVisitorId(request, response);
        return ApiResponse
                .ok(QnaResponse.from(boardQueryService.get(qnaId, visitorId)), "QnA 상세 조회 성공");
    }

    @GetMapping
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<QnA>> getAll(Pageable pageable) {
        Page<QnA> qnaList = boardQueryService.findAll(pageable);
        return ApiResponse.ok(CustomPageDto.of(qnaList),"QnA 목록 조회 성공");
    }

    @GetMapping("/{qnaId}/answer")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<AnswerResponse>> getAnswersByQnaId(
            @PathVariable String qnaId, Pageable pageable) {
        Page<Answer> answerPage = boardQueryService.findAnswersByQnaId(qnaId, pageable);
        return ApiResponse.ok(CustomPageDto.of(answerPage.map(AnswerResponse::from)),"QnA 답변 조회 성공");
    }

    @GetMapping("/search")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<KeywordSearchResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<KeywordSearchResponse> searchResult = boardQueryService.searchByKeyword(keyword, pageable).map(KeywordSearchResponse::from);
        return ApiResponse.ok(CustomPageDto.of(searchResult),"검색 성공");
    }
}

