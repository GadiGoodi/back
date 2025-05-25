package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.Answer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerResponse {
    private final String answerId;
    private final String nickname;
    private final String profileImage;
    private final LocalDateTime createDate;
    private final String content;
    private final Long replyCount;
    private final Long likeCount;
    private final Boolean isSelected;
    private final Boolean isLike;

    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .answerId(answer.answerId())
                .nickname(answer.nickname())
                .profileImage(answer.profileImage())
                .createDate(answer.createDate())
                .content(answer.content())
                .replyCount(answer.replyCount())
                .likeCount(answer.likeCount())
                .isSelected(answer.isSelected())
                .isLike(answer.isLike())
                .build();
    }
}

