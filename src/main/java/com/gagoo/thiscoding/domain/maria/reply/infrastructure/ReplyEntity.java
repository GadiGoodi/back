package com.gagoo.thiscoding.domain.maria.reply.infrastructure;

import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "reply")
public class ReplyEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_blind")
    private boolean isBlinded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "qna_id")
    private String qnaId;

    public static ReplyEntity from(Reply reply) {
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.id = reply.getId();
        replyEntity.content = reply.getContent();
        replyEntity.parentId = reply.getParentId();
        replyEntity.isBlinded = reply.isBlinded();
        replyEntity.user = UserEntity.from(reply.getUser());
        replyEntity.qnaId = reply.getQnaId();
        replyEntity.createDate = reply.getCreateDate();
        return replyEntity;
    }

    public Reply toModel() {
        return Reply.builder()
            .id(id)
            .content(content)
            .parentId(parentId)
            .isBlinded(isBlinded)
            .user(user.toModel())
            .qnaId(qnaId)
            .build();
    }
}
