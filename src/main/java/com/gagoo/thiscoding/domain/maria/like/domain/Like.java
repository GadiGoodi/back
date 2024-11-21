package com.gagoo.thiscoding.domain.maria.like.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class Like {

    private Long id;
    private User user;
    private Long qnaId;
}
