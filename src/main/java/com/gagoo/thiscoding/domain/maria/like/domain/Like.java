package com.gagoo.thiscoding.domain.maria.like.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {

    private Long id;
    private User user;
    private String qnaId;

    @Builder
    public Like(Long id, User user, String qnaId) {
        this.id = id;
        this.user = user;
        this.qnaId = qnaId;
    }

    public static Like create(User user, String qnaId) {
        return Like.builder()
                .user(user)
                .qnaId(qnaId)
                .build();
    }
}
