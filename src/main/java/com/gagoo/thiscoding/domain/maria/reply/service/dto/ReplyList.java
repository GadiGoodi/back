package com.gagoo.thiscoding.domain.maria.reply.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ReplyList {
    private Long replyId;
    private Long parentId;
    private int repliesCount;
    private String nickname;
    private String profileImage;
    private String content;
    private LocalDateTime createDate;

    public ReplyList(Long replyId, Long parent, int repliesCount, String nickname, String profileImage, String content, LocalDateTime createDate) {
        this.replyId = replyId;
        this.parentId = parent;
        this.repliesCount = repliesCount;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.content = content;
        this.createDate = createDate;
    }

    public ReplyList(Long replyId, Long parent, String nickname, String profileImage, String content, LocalDateTime createDate) {
        this.replyId = replyId;
        this.parentId = parent;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.content = content;
        this.createDate = createDate;
    }
}
