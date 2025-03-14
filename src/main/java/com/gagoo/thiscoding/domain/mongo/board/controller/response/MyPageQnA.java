package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
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

    public static MyPageQnA from(Board board){
        return MyPageQnA.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .language(board.getLanguage())
                .viewCount(board.getViewCount())
                .answerCount(board.getAnswerCount())
                .createDate(board.getCreateDate())
                .build();
    }
}
