package com.gagoo.thiscoding.domain.maria.bookmark.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.bookmark.controller.port.BookmarkService;
import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;
import com.gagoo.thiscoding.domain.maria.bookmark.service.exception.BookmarkNotFoundException;
import com.gagoo.thiscoding.domain.maria.bookmark.service.exception.ExistBookmark;
import com.gagoo.thiscoding.domain.maria.bookmark.service.port.BookmarkRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    /**
     * 게시글 북마크
     * @param qnaId
     * @return
     */
    @Override
    public Bookmark bookmarkQna(String qnaId) {
        validateQna(qnaId);
        validateBookmarkExists(qnaId);

        User currentUser = getCurrentUser();

        Bookmark bookmark = Bookmark.create(currentUser, qnaId);

        return bookmarkRepository.save(bookmark);
    }

    /**
     * 게시글 북마크 취소
     * @param qnaId
     */
    @Override
    public void cancelQnaBookmark(String qnaId) {
        validateQna(qnaId);

        Bookmark bookmark = getBookmarkByQnaIdAndUserId(qnaId, getCurrentUser().getId());

        bookmarkRepository.deleteById(bookmark.getId());
    }

    /**
     * 원본 게시글 존재, isBlind 여부 검증
     * @param qnaId
     */
    private void validateQna(String qnaId) {
        Board board = getBoardById(qnaId);

        validateIsBlind(board);
    }

    /**
     * 게시글 블라인드 여부 검증
     * @param board
     */
    private void validateIsBlind(Board board) {
        if(board.isBlind()) {
            throw new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND);
        }
    }

    /**
     * 게시글 북마크 여부 검증
     * @param qnaId
     */
    private void validateBookmarkExists(String qnaId) {
        if(bookmarkRepository.existsByQnaIdAndUserId(qnaId, getCurrentUser().getId())) {
            throw new ExistBookmark(ErrorCode.ALREADY_BOOKMARK);
        }
    }

    /**
     * 북마크 조회
     * @param qnaId
     * @param userId
     * @return
     */
    private Bookmark getBookmarkByQnaIdAndUserId(String qnaId, Long userId) {
        return bookmarkRepository.findByQnaIdAndUserId(qnaId, userId).orElseThrow(
                () -> new BookmarkNotFoundException(ErrorCode.BOOKMARK_NOT_FOUND)
        );
    }

    /**
     * Board 조회
     * @param qnaId
     * @return
     */
    private Board getBoardById(String qnaId) {
        return boardRepository.getById(qnaId);
    }

    /**
     * 로그인 사용자 조회
     * @return
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }
}
