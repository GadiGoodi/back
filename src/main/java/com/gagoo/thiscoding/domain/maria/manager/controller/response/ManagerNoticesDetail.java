package com.gagoo.thiscoding.domain.maria.manager.controller.response;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManagerNoticesDetail {

    private Long id;
    private User manager;
    private String title;
    private String content;
    private String category;
    private Long viewCount;


    public static ManagerNoticesDetail from(Manager manager) {
        return ManagerNoticesDetail.builder()
                .id(manager.getId())
                .manager(manager.getManager())
                .title(manager.getTitle())
                .content(manager.getContent())
                .category(manager.getCategory())
                .viewCount(manager.getViewCount())
                .build();
    }
}
