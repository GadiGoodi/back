package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.SearchResponse;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class BoardReadController {

    private final BoardService boardService;

    @GetMapping
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<CustomPageDto<Page<QnaList>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(boardService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<SearchResponse>> search(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        Page<SearchResponse> searchResult = boardService.searchByKeyword(keyword, page).map(SearchResponse::from);
        return ResponseEntity.ok(PageResponse.create(searchResult));
    }
}

