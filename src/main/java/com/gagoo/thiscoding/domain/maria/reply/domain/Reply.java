package com.gagoo.thiscoding.domain.maria.reply.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.ReplyEntity;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Reply {
    private Long id;
    private String qnaId;
    private User user;
    private String content;
    private Reply parent;
    private List<Reply> replies;
    private boolean isBlinded;
    private LocalDateTime createDate;

    @Builder
    public Reply(Long id, String qnaId, User user, String content, Reply parent,List<Reply> replies, boolean isBlinded, LocalDateTime createDate) {
        this.id = id;
        this.qnaId = qnaId;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.replies  = replies;
        this.isBlinded = isBlinded;
        this.createDate = createDate;
    }

    public static Reply create(User currentUser, String qnaId, ReplyCreate replyCreate, Reply parentComment) {
        return Reply.builder()
                .qnaId(qnaId)
                .user(currentUser)
                .content(replyCreate.getContent())
                .parent(parentComment)
                .isBlinded(false)
                .build();
    }

}
