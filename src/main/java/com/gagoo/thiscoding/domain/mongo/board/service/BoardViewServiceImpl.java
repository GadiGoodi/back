package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardView;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardViewServiceImpl implements BoardViewService {

    private final BoardViewRepository boardViewRepository;
    private final BoardRepository boardRepository;

    /**
     * 오늘 방문한 사용자인지 확인
     * - 방문한 사용자면 return
     * - 방문하지 않았다면 저장 후 조회수 증가
     */
    @Override
    public void processVisit(String qnaId, String visitorId) {
        if (isAlreadyVisit(qnaId, visitorId)) {
            return;
        }

        boardViewRepository.save(BoardView.record(qnaId, visitorId));
        boardRepository.incrementViewCount(qnaId);
    }

    private boolean isAlreadyVisit(String qnaId, String visitorId) {
        return boardViewRepository.existsByQnaAndVisitor(qnaId, visitorId);
    }
}
