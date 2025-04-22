package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.AnswerListResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.QnaResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.SearchResponse;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.AnswerList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
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

    private final BoardService boardService;
    private final VisitorIdProvider visitorIdProvider;

    @GetMapping("/{qnaId}")
    public ApiResponse<QnaResponse> get(@PathVariable String qnaId, HttpServletRequest request, HttpServletResponse response) {
        String visitorId = visitorIdProvider.getVisitorId(request, response);

        return ApiResponse
                .ok(QnaResponse.from(boardService.get(qnaId, visitorId)), "QnA 상세 조회 성공");
    }

    @GetMapping
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<QnaList>> getAll(Pageable pageable) {
        return ApiResponse.ok(boardService.findAll(pageable),"QnA 목록 조회 성공");
    }

    @GetMapping("/{qnaId}/answer")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<AnswerListResponse>> getAnswersByQnaId(
            @PathVariable String qnaId, Pageable pageable) {
        Page<AnswerList> answerPage = boardService.findAnswersByQnaId(qnaId, pageable);
        return ApiResponse.ok(CustomPageDto.of(answerPage.map(AnswerListResponse::from)),"QnA 답변 조회 성공");
    }

    @GetMapping("/search")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<SearchResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<SearchResponse> searchResult = boardService.searchByKeyword(keyword, pageable).map(SearchResponse::from);
        return ApiResponse.ok(CustomPageDto.of(searchResult),"검색 성공");
    }
}

