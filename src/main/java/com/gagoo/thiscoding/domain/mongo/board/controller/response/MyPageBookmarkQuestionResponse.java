package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookmarkQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageBookmarkQuestionResponse {

    private final String qnaId;
    private final String language;
    private final String title;
    private final String content;
    private final Long viewCount;
    private final Long answerCount;
    private final Boolean isAdopted;
    private final LocalDateTime createDate;

    public static MyPageBookmarkQuestionResponse from(MyPageBookmarkQuestion myPageBookMarkQuestion){
        return MyPageBookmarkQuestionResponse.builder()
                .qnaId(myPageBookMarkQuestion.qnaId())
                .language(myPageBookMarkQuestion.language())
                .title(myPageBookMarkQuestion.title())
                .content(myPageBookMarkQuestion.content())
                .viewCount(myPageBookMarkQuestion.viewCount())
                .answerCount(myPageBookMarkQuestion.answerCount())
                .isAdopted(myPageBookMarkQuestion.isAdopted())
                .createDate(myPageBookMarkQuestion.createDate())
                .build();
    }
}
