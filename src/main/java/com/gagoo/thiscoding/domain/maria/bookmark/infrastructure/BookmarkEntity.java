package com.gagoo.thiscoding.domain.maria.bookmark.infrastructure;

import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "bookmark")
public class BookmarkEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "qna_id")
    private String qnaId;

    public Bookmark toModel() {
        return Bookmark.builder()
                .id(this.id)
                .user(this.user.toModel())
                .qnaId(this.qnaId)
                .build();
    }

    public static BookmarkEntity from(Bookmark bookmark) {
        BookmarkEntity entity = new BookmarkEntity();
        entity.id = bookmark.getId();
        entity.user = UserEntity.from(bookmark.getUser());
        entity.qnaId = bookmark.getQnaId();
        return entity;
    }
}
