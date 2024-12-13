package com.gagoo.thiscoding.domain.maria.manager.domain;

import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerNoticesCreate {

    private Long id;
    private User manager;
    private String title;
    private String content;
    private String category;


    //Domain > DTO 코드
    public static ManagerNoticesCreate from(Manager manager) {
      return ManagerNoticesCreate.builder()
              .id(manager.getId())
              .manager(manager.getManager())
              .title(manager.getTitle())
              .content(manager.getContent())
              .category(manager.getCategory())
              .build();
    }

    public ManagerEntity toEntity() {
        return ManagerEntity.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}