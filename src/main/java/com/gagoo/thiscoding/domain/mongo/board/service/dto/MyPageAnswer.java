package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import java.time.LocalDateTime;

public record MyPageAnswer(
        String answerId,
        String parentId,
        String qnaLanguage,
        String qnaTitle,
        String content,
        Long replyCount,
        Boolean isAdopted,
        LocalDateTime createDate
) {}