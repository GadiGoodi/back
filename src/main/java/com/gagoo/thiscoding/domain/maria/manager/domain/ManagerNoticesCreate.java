package com.gagoo.thiscoding.domain.maria.manager.domain;

import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ManagerNoticesCreate {

    private Long id;
    private User manager;
    private String title;
    private String content;
    private String category;
}
