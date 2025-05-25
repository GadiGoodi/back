package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageQuestionResponse {

    private String qnaId;
    private String title;
    private String content;
    private String nickname;
    private String language;
    private Long viewCount;
    private Long answerCount;
    private LocalDateTime createDate;

    public static MyPageQuestionResponse from(MyPageQuestion myPageQuestion){
        return MyPageQuestionResponse.builder()
                .qnaId(myPageQuestion.qnaId())
                .title(myPageQuestion.title())
                .content(myPageQuestion.content())
                .language(myPageQuestion.language())
                .viewCount(myPageQuestion.viewCount())
                .answerCount(myPageQuestion.answerCount())
                .createDate(myPageQuestion.createDate())
                .build();
    }
}
