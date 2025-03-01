package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final SecurityUtils securityUtils;

    /**
     * qna 등록
     */
    @Override
    @Transactional
    public Board create(BoardCreate boardCreate) {
        User currentUser = getCurrentUser();
        Board board = Board.create(currentUser, boardCreate);

        return boardRepository.save(board);
    }

    /**
     * qna 답변 작성
     */
    @Override
    public List<Board> writeAnswer(String parentQnaId, BoardAnswer boardAnswer) {
        User currentUser = getCurrentUser();

        Board parentBoard = boardRepository.getById(parentQnaId);
        parentBoard.addAnswerCount();

        Board answer = Board.writeAnswer(currentUser, parentQnaId, boardAnswer.content());

        return boardRepository.saveAll(List.of(answer, parentBoard));
    }

    /**
     * qna 상세조회
     */
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
    public Page<Search> searchByKeyword(String keyword, Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), PageSize.QNA);
        return boardRepository.findByTitleOrContent(keyword, keyword, customPageable);
    }

    /**
     * 마이페이지 내가 작성한 Qna 조회
     */
    @Override
    public Page<Board> getMyPagePostQnA(Pageable pageable) {
        User currentUser = getCurrentUser();
        return boardRepository.findByUserId(currentUser.getId(), pageable);
    }

    /**
     * 게시판 전체목록 조회
     */
    @Override
    public CustomPageDto<QnaList> findAll(Pageable pageable) {
        Page<QnaList> qnaListPage = boardRepository.findAll(pageable).map(
                QnaList::from
        );

        return CustomPageDto.of(qnaListPage);
    }

    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }
}
