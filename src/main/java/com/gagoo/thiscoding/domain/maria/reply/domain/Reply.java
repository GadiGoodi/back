package com.gagoo.thiscoding.domain.maria.reply.domain;

import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Reply {
    private Long id;
    private String qnaId;
    private User user;
    private String content;
    private Long parentId;
    private boolean isBlinded;
    private LocalDateTime createDate;

    @Builder
    public Reply(Long id, String qnaId, User user, String content, Long parentId, boolean isBlinded, LocalDateTime createDate) {
        this.id = id;
        this.qnaId = qnaId;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.isBlinded = isBlinded;
        this.createDate = createDate;
    }

    public static Reply create(User currentUser, String qnaId, ReplyCreate replyCreate) {
        return Reply.builder()
                .qnaId(qnaId)
                .user(currentUser)
                .content(replyCreate.getContent())
                .parentId(replyCreate.getParentId())
                .isBlinded(false)
                .build();
    }
}
