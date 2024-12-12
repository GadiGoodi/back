package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageQnaService;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyPageQnA;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageQnaServiceImpl implements MyPageQnaService {

    private final BoardRepository boardRepository;

    @Override
    public Page<Board> getMyPagePostQnA(Long userId, Pageable pageable) {
        return boardRepository.findByUserId(userId,pageable);
    }

}
