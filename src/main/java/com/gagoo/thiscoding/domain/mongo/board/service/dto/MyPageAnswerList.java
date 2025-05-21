package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import java.time.LocalDateTime;

public record MyPageAnswerList(String answerId, String nickname,String profileImage, String content, String parentId,
                               Long likeCount, Long replyCount,Boolean isSelected, LocalDateTime createDate) {

    public static MyPageAnswerList of(Board board) {
        return new MyPageAnswerList(
                board.getId(),
                board.getNickname(),
                board.getProfileImg(),
                board.getContent(),
                board.getParentId(),
                board.getLikeCount(),
                board.getReplyCount(),
                board.isSelected(),
                board.getCreateDate()
        );
    }
}