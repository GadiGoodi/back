package com.gagoo.thiscoding.domain.maria.manager.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class Manager {
    private Long id;
    private User manager;
    private String title;
    private String content;
    private String category;
    private Long viewCount;
}
