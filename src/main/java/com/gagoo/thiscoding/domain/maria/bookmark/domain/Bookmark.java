package com.gagoo.thiscoding.domain.maria.bookmark.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class Bookmark {

    private Long id;
    private User user;
    private Long qnaId;
}
