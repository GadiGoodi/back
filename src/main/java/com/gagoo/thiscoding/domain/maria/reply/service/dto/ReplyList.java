package com.gagoo.thiscoding.domain.maria.reply.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyList {
    private final Long replyId;
    private final Long parentId;
    private final String nickname;
    private final String profileImage;
    private final String content;
    private final LocalDateTime createDate;

    @Builder
    public ReplyList(Long replyId, Long parentId, String nickname, String profileImage, String content, LocalDateTime createDate) {
        this.replyId = replyId;
        this.parentId = parentId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.content = content;
        this.createDate = createDate;
    }
}
