package com.gagoo.thiscoding.domain.maria.reply.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class Reply {
    private Long id;
    private Long qnaId;
    private User user;
    private String content;
    private Long parentId;
    private boolean isBlinded;
}
