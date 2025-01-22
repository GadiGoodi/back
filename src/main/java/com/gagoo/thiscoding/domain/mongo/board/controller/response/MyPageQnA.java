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
    private Long userId;
    private String title;
    private String content;
    private String language;
    private Long likeCount;
    private Long viewCount;
    private String parentId;
    private boolean isSelected;


    public static MyPageQnA from(Board board){
        return MyPageQnA.builder()
                .id(board.getId())
                .userId(board.getUserId())
                .title(board.getTitle())
                .content(board.getContent())
                .language(board.getLanguage())
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .parentId(board.getParentId())
                .isSelected(board.isSelected())
                .build();
    }
}
