package com.gagoo.thiscoding.domain.maria.reply.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Reply {
    private Long id;
    private Long qnaId;
    private User user;
    private String content;
    private Long parentId;
    private boolean isBlinded;

    @Builder
    public Reply(Long id, Long qnaId, User user, String content, Long parentId, boolean isBlinded) {
        this.id = id;
        this.qnaId = qnaId;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.isBlinded = isBlinded;
    }
}
