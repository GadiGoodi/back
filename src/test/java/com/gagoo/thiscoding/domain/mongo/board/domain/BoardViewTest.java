package com.gagoo.thiscoding.domain.mongo.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


@DisplayName("QNA 방문자 저장 테스트")
class BoardViewTest {

    @Test
    @DisplayName("QNA에 방문하면 qnaId와 visitorId가 저장된다")
    void qnaId와_visitorId로_저장할_수_있다() {
        // Given
        String qnaId = "qna123";
        String visitorId = "visitor456";
        LocalDate today = LocalDate.now();
        // When
        BoardView boardView = BoardView.record(qnaId, visitorId);

        // Then
        assertThat(boardView).isNotNull();
        assertThat(boardView.getQnaId()).isEqualTo(qnaId);
        assertThat(boardView.getVisitorId()).isEqualTo(visitorId);
        assertThat(boardView.getViewDate()).isEqualTo(today);
    }
}