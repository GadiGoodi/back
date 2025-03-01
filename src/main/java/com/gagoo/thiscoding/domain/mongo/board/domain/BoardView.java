package com.gagoo.thiscoding.domain.mongo.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class BoardView {
    private String id;
    private String qnaId;
    private String visitorId;
    private LocalDate viewDate;
    private LocalDateTime createDate;

    public static BoardView record(String qnaId, String visitorId) {
        return BoardView.builder()
                .qnaId(qnaId)
                .visitorId(visitorId)
                .viewDate(LocalDate.now())
                .build();
    }
}
