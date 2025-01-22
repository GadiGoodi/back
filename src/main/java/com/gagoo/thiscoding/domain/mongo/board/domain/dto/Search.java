package com.gagoo.thiscoding.domain.mongo.board.domain.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Search {
    private String id;
    private Long userId;
    private String title;
    private String content;
    private String language;
    private String parentId;
    private Long likeCount;
    private Long viewCount;
    private Long answerCount;
    private boolean isBlind;
    private boolean isSelected;
    private LocalDateTime createDate;

    public static Search from(BoardDocument board) {
        return Search.builder()
            .id(board.getId())
            .userId(board.getUserId())
            .title(board.getTitle())
            .content(board.getContent())
            .language(board.getLanguage())
            .parentId(board.getParentId())
            .likeCount(board.getLikeCount())
            .viewCount(board.getViewCount())
            .answerCount(board.getAnswerCount())
            .isBlind(board.isBlind())
            .isSelected(board.isSelected())
            .createDate(board.getCreateDate())
            .build();
    }
}
