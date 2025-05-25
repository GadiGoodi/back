package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import java.time.LocalDateTime;

public record MyPageQuestion(
        String qnaId,
        String language,
        String title,
        String content,
        Long viewCount,
        Long answerCount,
        Boolean isAdopted,
        LocalDateTime createDate)
{}