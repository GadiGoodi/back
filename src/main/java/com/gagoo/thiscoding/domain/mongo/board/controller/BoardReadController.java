package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.QnaResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.SearchResponse;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class BoardReadController {

    private final BoardService boardService;

    @GetMapping("/{qnaId}")
    public ResponseEntity<QnaResponse> get(@PathVariable String qnaId, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity
                .ok()
                .body(QnaResponse.from(boardService.get(qnaId, request, response)));
    }

    @GetMapping
    @ConvertToOneBase
    public ResponseEntity<CustomPageDto<QnaList>> getAll(Pageable pageable) {
        return ResponseEntity.ok(boardService.findAll(pageable));
    }

    @GetMapping("/search")
    @ConvertToOneBase
    public ResponseEntity<CustomPageDto<SearchResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<SearchResponse> searchResult = boardService.searchByKeyword(keyword, pageable).map(SearchResponse::from);
        return ResponseEntity.ok(CustomPageDto.of(searchResult));
    }
}

