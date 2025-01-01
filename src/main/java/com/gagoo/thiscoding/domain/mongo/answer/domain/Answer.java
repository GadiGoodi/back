package com.gagoo.thiscoding.domain.mongo.answer.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.answer.domain.dto.AnswerCreate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Answer {

    private final String id;

    private final String boardId;

    private final Long userId;

    private final String content;

    private final Long likeCount;

    private final boolean isBlind;

    private final boolean isSelected;

    @Builder
    public Answer(String id, String boardId, Long userId, String content, Long likeCount, boolean isBlind, boolean isSelected) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.content = content;
        this.likeCount = likeCount;
        this.isBlind = isBlind;
        this.isSelected = isSelected;
    }

    public static Answer create(User currentUser, AnswerCreate answerCreate) {
        return Answer.builder()
                .boardId(answerCreate.getBoardId())
                .userId(currentUser.getId())
                .content(answerCreate.getContent())
                .likeCount(0L)
                .isBlind(false)
                .isSelected(false)
                .build();
    }
}
