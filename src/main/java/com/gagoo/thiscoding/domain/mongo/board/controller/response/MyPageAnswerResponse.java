package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageAnswerResponse {

    private String answerId;
    private String parentId;
    private String qnaLanguage;
    private String qnaTitle;
    private String content;
    private Long replyCount;
    private Boolean isAdopted;
    private LocalDateTime createDate;

    public static MyPageAnswerResponse from(MyPageAnswer myPageAnswer){
        return MyPageAnswerResponse.builder()
                .answerId(myPageAnswer.answerId())
                .parentId(myPageAnswer.parentId())
                .qnaLanguage(myPageAnswer.qnaLanguage())
                .qnaTitle(myPageAnswer.qnaTitle())
                .content(myPageAnswer.content())
                .replyCount(myPageAnswer.replyCount())
                .isAdopted(myPageAnswer.isAdopted())
                .createDate(myPageAnswer.createDate())
                .build();
    }
}
