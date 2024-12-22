package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.SearchDto;
import com.gagoo.thiscoding.global.paging.dto.PageResponseFactory;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PageResponse<SearchDto>> search(@RequestParam String query, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(
            PageResponseFactory.create(
                boardService.search(query, page).map(SearchDto::from)
            )
        );
    }
}
