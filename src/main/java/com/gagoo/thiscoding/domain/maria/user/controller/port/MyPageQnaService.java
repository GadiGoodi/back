package com.gagoo.thiscoding.domain.maria.user.controller.port;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyPageQnaService {
    Page<Board> getMyPagePostQnA(Long userId, Pageable pageable);
}
