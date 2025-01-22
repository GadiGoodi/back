package com.gagoo.thiscoding.domain.mongo.board.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Board {
    private String id;
    private Long userId;
    private String nickname;
    private String profileImg;
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

    public static Board create(User user, BoardCreate boardCreate) {
        return Board.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getImageUrl())
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .language(boardCreate.getLanguage())
                .parentId(boardCreate.getParentId())
                .likeCount(0L)
                .viewCount(0L)
                .answerCount(0L)
                .isBlind(false)
                .isSelected(false)
                .build();
    }
}