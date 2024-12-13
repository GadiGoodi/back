package com.gagoo.thiscoding.domain.maria.manager.controller.response;

import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerNoticesList extends BaseTimeEntity {

    private Long id;
    private User manager;
    private String title;
    private String category;
    private Long viewCount;
    private LocalDateTime createDate;


    //Domain > DTO 코드
    public static ManagerNoticesList from(Manager manager) {
      return ManagerNoticesList.builder()
              .id(manager.getId())
              .manager(manager.getManager())
              .title(manager.getTitle())
              .category(manager.getCategory())
              .viewCount(manager.getViewCount())
              .createDate(manager.getCreateDate())
              .build();
    }
}
