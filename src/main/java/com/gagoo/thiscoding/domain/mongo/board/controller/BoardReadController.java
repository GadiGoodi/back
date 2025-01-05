package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.SearchResponse;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class BoardReadController {
    private final BoardService boardService;

    @GetMapping("/search")
    public ResponseEntity<PageResponse<SearchResponse>> search(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        Page<SearchResponse> searchResult = boardService.searchByKeyword(keyword, page).map(SearchResponse::from);
        return ResponseEntity.ok(PageResponse.create(searchResult));
    }
}

