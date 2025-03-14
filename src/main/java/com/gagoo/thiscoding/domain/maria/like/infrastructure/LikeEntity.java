package com.gagoo.thiscoding.domain.maria.like.infrastructure;

import com.gagoo.thiscoding.domain.maria.like.domain.Like;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "qna_id")
    private String qnaId;

    public Like toModel() {
        return Like.builder()
                .id(this.id)
                .user(this.user.toModel())
                .qnaId(this.qnaId)
                .build();
    }

    public static LikeEntity from(Like like) {
        LikeEntity entity = new LikeEntity();
        entity.id = like.getId();
        entity.user = UserEntity.from(like.getUser());
        entity.qnaId = like.getQnaId();
        return entity;
    }
}
