package com.gagoo.thiscoding.domain.maria.bookmark.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Bookmark {

    private Long id;
    private User user;
    private String qnaId;

    @Builder
    public Bookmark(Long id, User user, String qnaId) {
        this.id = id;
        this.user = user;
        this.qnaId = qnaId;
    }

    public static Bookmark create(User user, String qnaId) {
        return Bookmark.builder()
                .user(user)
                .qnaId(qnaId)
                .build();
    }
}
