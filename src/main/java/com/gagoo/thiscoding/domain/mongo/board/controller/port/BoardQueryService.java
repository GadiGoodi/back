package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardQueryService {
    QnaDetail get(String qnaId, String visitorId);
    Page<KeywordSearchResult> searchByKeyword(String keyword, Pageable pageable);
    Page<MyPageQuestion> getMyPagePostQnA(Pageable pageable);
    Page<MyPageAnswer> getMyPagePostAnswer(Pageable pageable);
    Page<MyPageBookmarkQuestion> getMyPageBookMarkQuestion(Pageable pageable);
    Page<QnA> findAll(Pageable pageable);
    Page<Answer> findAnswersByQnaId(String qnaId, Pageable pageable);
}
