package com.gagoo.thiscoding.domain.maria.reply.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ReplyList {
    private final Long replyId;
    private final Long parentId; // parentId 유지
    private final List<ReplyList> replies = new ArrayList<>(); // 대댓글 리스트
    private final String nickname;
    private final String profileImage;
    private final String content;
    private final LocalDateTime createDate;

    @Builder
    public ReplyList(Long replyId, Long parent, String nickname, String profileImage, String content, LocalDateTime createDate) {
        this.replyId = replyId;
        this.parentId = parent;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.content = content;
        this.createDate = createDate;
    }
}
