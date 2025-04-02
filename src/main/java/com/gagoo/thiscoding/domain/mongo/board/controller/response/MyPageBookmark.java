package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookMarkList;
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
public class MyPageBookmark {

    private String qnaId;
    private String title;
    private String content;
    private String nickname;
    private String language;
    private Long viewCount;
    private Long answerCount;
    private LocalDateTime createDate;

    public static MyPageBookmark from(MyPageBookMarkList myPageBookMarkList){
        return MyPageBookmark.builder()
                .qnaId(myPageBookMarkList.qnaId())
                .title(myPageBookMarkList.title())
                .content(myPageBookMarkList.content())
                .nickname(myPageBookMarkList.nickname())
                .language(myPageBookMarkList.language())
                .viewCount(myPageBookMarkList.viewCount())
                .answerCount(myPageBookMarkList.answerCount())
                .createDate(myPageBookMarkList.createDate())
                .build();
    }
}
