package com.gagoo.thiscoding.domain.maria.manager.domain;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Builder
public class Manager {
    //record로 변경해보기
    private Long id;
    private User manager;
    private String title;
    private String content;
    private String category;
    private Long viewCount;
    private LocalDateTime createDate;


    //    Entity > Domain 코드를 작성
    public Manager(Long id, User manager, String title, String content, String category, Long viewCount, LocalDateTime createDate) {
        this.id = id;
        this.manager = manager;
        this.title = title;
        this.content = content;
        this.category = category;
        this.viewCount = viewCount;
        this.createDate = createDate;
    }

    //공지사항 목록
    public Manager(Long id, User manager, String title, String category, Long viewCount,LocalDateTime createDate) {
        this.id = id;
        this.manager = manager;
        this.title = title;
        this.category = category;
        this.viewCount = viewCount;
        this.createDate = createDate;
    }

    public static Manager create(ManagerNoticesCreate managerNoticesCreate) {
        return Manager.builder()
                .id(managerNoticesCreate.getId())
                .manager(managerNoticesCreate.getManager())
                .title(managerNoticesCreate.getTitle())
                .content(managerNoticesCreate.getContent())
                .category(managerNoticesCreate.getCategory())
                .build();
    }

    public ManagerEntity toEntity() {
        return ManagerEntity.builder()
                .id(this.id) // ID가 엔티티에서도 필요하다면 포함
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .viewCount(this.viewCount)
                .build();
    }

    public Manager updateManagerNotices(ManagerNoticesUpdate managerNoticesPost){
        this.title = managerNoticesPost.getTitle();
        this.content = managerNoticesPost.getContent();

        return this;
    }
}
