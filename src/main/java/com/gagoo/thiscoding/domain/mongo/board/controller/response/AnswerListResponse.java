package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.AnswerList;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerListResponse {
    private final String answerId;
    private final String nickname;
    private final String profileImage;
    private final LocalDateTime createDate;
    private final String content;
    private final Long replyCount;
    private final Long likeCount;

    public static AnswerListResponse from(AnswerList answer) {
        return AnswerListResponse.builder()
                .answerId(answer.answerId())
                .nickname(answer.nickname())
                .profileImage(answer.profileImage())
                .createDate(answer.createDate())
                .content(answer.content())
                .replyCount(answer.replyCount())
                .likeCount(answer.likeCount())
                .build();
    }
}

