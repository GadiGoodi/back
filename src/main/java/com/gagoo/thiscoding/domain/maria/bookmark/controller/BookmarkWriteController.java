package com.gagoo.thiscoding.domain.maria.bookmark.controller;

import com.gagoo.thiscoding.domain.maria.bookmark.controller.port.BookmarkService;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class BookmarkWriteController {

    private final BookmarkService bookmarkService;

    // 북마크
    @PostMapping("/{qnaId}/bookmark")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> bookmarkQna(@PathVariable String qnaId) {
        bookmarkService.bookmarkQna(qnaId);

        return ResponseEntity.ok("게시글 북마크가 완료되었습니다.");
    }

    // 북마크 취소
    @DeleteMapping("/{qnaId}/cancel-bookmark")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> cancelQnaBookmark(@PathVariable String qnaId) {
        bookmarkService.cancelQnaBookmark(qnaId);

        return ResponseEntity.ok("북마크를 취소하였습니다.");
    }
}
