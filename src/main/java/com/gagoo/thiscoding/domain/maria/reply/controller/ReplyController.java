package com.gagoo.thiscoding.domain.maria.reply.controller;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> delete(@PathVariable Long replyId) {
        replyService.delete(replyId);
        return ResponseEntity
            .ok()
            .body("댓글이 삭제되었습니다.");
    }
}
