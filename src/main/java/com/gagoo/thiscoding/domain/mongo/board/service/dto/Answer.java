package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;

import java.time.LocalDateTime;

public record Answer(
        String answerId,
        String nickname,
        String profileImage,
        String content,
        Long likeCount,
        Long replyCount,
        boolean isSelected,
        boolean isLike,
        LocalDateTime createDate
        ) {

    public static Answer of(Board board, BoardStats stats, Boolean isLike) {
        return new Answer(
                board.getId(),
                board.getNickname(),
                board.getProfileImg(),
                board.getContent(),
                stats.getLikeCount(),
                stats.getReplyCount(),
                board.isSelected(),
                isLike,
                board.getCreateDate()
                );
    }
}

