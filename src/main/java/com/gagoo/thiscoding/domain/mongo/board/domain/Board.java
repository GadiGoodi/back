package com.gagoo.thiscoding.domain.mongo.board.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.InvalidAnswerUserException;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.NotQnaUserException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
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
    private boolean isBlind;
    private boolean isSelected;
    private boolean isAdopted;
    private LocalDateTime createDate;

    /**
     * qna 작성
     */
    public static Board write(User user, BoardCreate boardCreate) {
        return Board.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getImageUrl())
                .title(boardCreate.title())
                .content(boardCreate.content())
                .language(boardCreate.language())
                .isBlind(false)
                .isAdopted(false)
                .build();
    }

    /**
     * 답변 작성
     */
    public static Board writeAnswer(User user, String parentId, String content) {
        return Board.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getImageUrl())
                .content(content)
                .parentId(parentId)
                .isBlind(false)
                .isSelected(false)
                .build();
    }

    /**
     * 답변 채택 유효성 검사
     */
    public void validateAdoptable(Long currentUserId, Long parentUserId) {
        if (!parentUserId.equals(currentUserId)) {
            throw new NotQnaUserException(ErrorCode.NOT_QNA_USER);
        }

        if (parentUserId.equals(this.userId)) {
            throw new InvalidAnswerUserException(ErrorCode.INVALID_ANSWER_USER);
        }
    }
}