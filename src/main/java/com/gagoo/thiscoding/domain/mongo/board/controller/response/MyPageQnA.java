package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQnAList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageQnA {

    private String id;
    private String title;
    private String content;
    private String nickname;
    private String language;
    private Long viewCount;
    private Long answerCount;
    private LocalDateTime createDate;

    public static MyPageQnA from(MyPageQnAList myPageQnAList){
        return MyPageQnA.builder()
                .id(myPageQnAList.id())
                .title(myPageQnAList.title())
                .content(myPageQnAList.content())
                .nickname(myPageQnAList.nickname())
                .language(myPageQnAList.language())
                .viewCount(myPageQnAList.viewCount())
                .answerCount(myPageQnAList.answerCount())
                .createDate(myPageQnAList.createDate())
                .build();
    }
}
