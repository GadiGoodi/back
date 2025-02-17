package com.gagoo.thiscoding.domain.maria.reply.infrastructure;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import com.mongodb.lang.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Table(name = "reply")
public class ReplyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ReplyEntity parent;  // 셀프 조인

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ReplyEntity> replies = new ArrayList<>();

    @Column(name = "is_blind")
    private boolean isBlinded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "qna_id")
    private String qnaId;

    public static ReplyEntity from(Reply reply) {
        if(reply == null){
            return null;
        }
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.id = reply.getId();
        replyEntity.content = reply.getContent();
        replyEntity.parent = ReplyEntity.from(reply.getParent());
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
                .parent(Optional.ofNullable(parent).map(ReplyEntity::toModel).orElse(null))
                .isBlinded(isBlinded)
                .user(user.toModel())
                .qnaId(qnaId)
                .build();
    }
}
