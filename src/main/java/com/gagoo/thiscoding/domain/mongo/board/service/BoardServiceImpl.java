package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.paging.PagingProcessor;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    /**
     * qna 등록
     */
    @Override
    public Board create(BoardCreate boardCreate) {
        Board board = Board.create(boardCreate);

        return boardRepository.save(board);
    }

    /**
     * QnA 제목 + 내용 검색
     */
    @Override
    public Page<Search> searchByKeyword(String keyword, int page) {
        return boardRepository.findByTitleOrContent(keyword, keyword, PagingProcessor.toPageable(page, PageSize.QNA));
    }

    @Override
    public Page<Board> getMyPagePostQnA(Pageable pageable) {
        User currentUser = userService.getByEmail(SecurityUtils.getUserEmail());
        return boardRepository.findByUserId(currentUser.getId(), pageable);
    }
}
