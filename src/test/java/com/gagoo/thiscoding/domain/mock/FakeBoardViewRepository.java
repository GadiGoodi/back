package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardView;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeBoardViewRepository implements BoardViewRepository {

    private Long idCounter = 0L;
    private final List<BoardView> data = new ArrayList<>();

    @Override
    public BoardView save(BoardView boardView) {
        if (boardView.getId() == null || boardView.getId().isBlank()) {
            BoardView saveBoardView = BoardView.builder()
                    .id(generateTestId())
                    .qnaId(boardView.getQnaId())
                    .visitorId(boardView.getVisitorId())
                    .viewDate(boardView.getViewDate())
                    .createDate(LocalDateTime.now())
                    .build();
            data.add(saveBoardView);
            return saveBoardView;
        } else {
            data.removeIf(item -> item.getId().equals(boardView.getId()));
            data.add(boardView);
            return boardView;
        }
    }

    @Override
    public boolean existsByQnaAndVisitor(String qnaId, String visitorId) {
        return data.stream()
                .anyMatch(boardView -> boardView.getQnaId().equals(qnaId)
                        && boardView.getVisitorId().equals(visitorId)
                        && boardView.getViewDate().equals(LocalDate.now()));
    }

    private String generateTestId() {
        return "test-board-" + ++idCounter;  // 예측 가능한 테스트용 ID 생성
    }

}
