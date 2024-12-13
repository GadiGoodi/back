package com.gagoo.thiscoding.domain.maria.manager.infrastructure;

import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "manager_board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_board_id")
    private Long id;

    private String title;

    private String content;

    private String category;

    @Column(name = "view_count",columnDefinition = "integer default 0",nullable = false)
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity manager;



    //Domain > Entity 코드
    public static ManagerEntity from(Manager manager) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.title = manager.getTitle();
        managerEntity.content = manager.getContent();
        managerEntity.category = manager.getCategory();
        managerEntity.viewCount = manager.getViewCount();

        return managerEntity;
    }

    //Domain > Entity 코드
    public static ManagerEntity from(ManagerNoticesCreate manager) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.title = manager.getTitle();
        managerEntity.content = manager.getContent();
        managerEntity.category = manager.getCategory();

        return managerEntity;
    }

    //Entity > Domain 코드
    public Manager toModel() {
        return Manager.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .viewCount(viewCount)
                .createDate(getCreateDate())
                .build();
    }
}
