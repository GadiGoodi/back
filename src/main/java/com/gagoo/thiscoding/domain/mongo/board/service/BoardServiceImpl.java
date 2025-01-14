package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.paging.PagingProcessor;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    /**
     * qna 등록
     */
    @Override
    public Board create(BoardCreate boardCreate) {
        User currentUser = userRepository.getByEmail(SecurityUtils.getUserEmail());

        Board board = Board.create(currentUser, boardCreate);

        return boardRepository.save(board);
    }

    @Override
    public QnaDetail get(String qnaId) {
        Board qnaDetail = boardRepository.getById(qnaId);
        Long replyCount = replyRepository.countByQnaId(qnaId);

        return QnaDetail.from(qnaDetail, replyCount);
    }

    /**
     * QnA 제목 + 내용 검색
     */
    @Override
    public Page<Search> searchByKeyword(String keyword, int page) {
        return boardRepository.findByTitleOrContent(keyword, keyword, PagingProcessor.toPageable(page, PageSize.QNA));
    }

    /**
     * 마이페이지 내가 작성한 Qna 조회
     */

    @Override
    public Page<Board> getMyPagePostQnA(Pageable pageable) {
        User currentUser = userRepository.getByEmail(SecurityUtils.getUserEmail());
        return boardRepository.findByUserId(currentUser.getId(), pageable);
    }

    @Override
    public CustomPageDto<Page<QnaList>> findAll(Pageable pageable) {
        Page<QnaList> qnaListPage = boardRepository.findByParentIdIsNull(pageable).map(
                qna -> QnaList.from(qna)
        );

        return new CustomPageDto(qnaListPage);
    }
}
