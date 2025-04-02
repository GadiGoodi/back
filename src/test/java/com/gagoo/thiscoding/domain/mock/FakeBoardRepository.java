package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FakeBoardRepository implements BoardRepository {

    private Long idCounter = 0L;
    private final List<Board> data = new ArrayList<>();

    @Override
    public Board save(Board board) {
        if (board.getId() == null || board.getId().isBlank()) {
            Board saveBoard = Board.builder()
                    .id(generateTestId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount())
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount())
                    .replyCount(board.getReplyCount())
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(LocalDateTime.now())
                    .build();

            data.add(saveBoard);
            return saveBoard;
        } else {
            data.removeIf(item -> item.getId().equals(board.getId()));
            data.add(board);
            return board;
        }
    }

    @Override
    public Board getById(String qnaId) {
        return findById(qnaId).orElseThrow(
                () -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND)
        );
    }

    @Override
    public List<Long> getTop10Users() {
        return null;
    }

    @Override
    public Page<Board> findByUserId(Long userId, Pageable pageable) {
        if (pageable.getPageSize() < 1) {
            return Page.empty(pageable);
        }
        List<Board> findUserId = data.stream()
                .filter(board -> board.getUserId().equals(userId))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), findUserId.size());

        if (start >= findUserId.size()) {
            return Page.empty(pageable);
        }

        List<Board> pageableBoard = findUserId.subList(start, end);

        return PageableExecutionUtils.getPage(
                pageableBoard,
                pageable,
                findUserId::size);
    }

    @Override
    public Page<Board> findByUserIdAndParentIdIsNotRoot(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Search> findByTitleOrContent(String title, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Board> findAnswerByQnaId(String qnaId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Board> findAnswerByQnaIdSortByIsSelected(String qnaId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Board> findByIdIn(List<String> qnaId, Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsById(String qnaId) {
        return data.stream().anyMatch(board -> board.getId().equals(qnaId));
    }

    @Override
    public boolean existsByParentIdAndIsSelectedIsTrue(String qnaId) {
        return data.stream()
                .anyMatch(board -> board.getParentId().equals(qnaId)
                        && board.isSelected());
    }

    @Override
    public void incrementViewCount(String boardId) {
        findById(boardId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount())
                    .viewCount(board.getViewCount() + 1)
                    .answerCount(board.getAnswerCount())
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(boardId));
            data.add(updatedBoard);
        });
    }

    @Override
    public void incrementAnswerCount(String parentQnaId) {
        findById(parentQnaId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount())
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount() + 1)
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(parentQnaId));
            data.add(updatedBoard);
        });
    }

    @Override
    public void incrementReplyCount(String qnaId) {
        findById(qnaId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount())
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount())
                    .replyCount(board.getReplyCount() + 1)
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(qnaId));
            data.add(updatedBoard);
        });
    }

    @Override
    public void incrementLikeCount(String qnaId) {
        findById(qnaId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount() + 1)
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount())
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(qnaId));
            data.add(updatedBoard);
        });
    }

    @Override
    public void decrementLikeCount(String qnaId) {
        findById(qnaId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount() - 1)
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount())
                    .isSelected(board.isSelected())
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(qnaId));
            data.add(updatedBoard);
        });
    }

    @Override
    public void adoptAnswer(String qnaId) {
        findById(qnaId).ifPresent(board -> {
            Board updatedBoard = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .language(board.getLanguage())
                    .content(board.getContent())
                    .parentId(board.getParentId())
                    .likeCount(board.getLikeCount())
                    .viewCount(board.getViewCount())
                    .answerCount(board.getAnswerCount())
                    .isSelected(true)
                    .userId(board.getUserId())
                    .createDate(board.getCreateDate())
                    .build();

            data.removeIf(item -> item.getId().equals(qnaId));
            data.add(updatedBoard);
        });
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        if (pageable.getPageSize() < 1) {
            return Page.empty(pageable);
        }

        List<Board> findAllBoard = data.stream()
                .filter(board -> Objects.equals("root", board.getParentId()))
                .sorted(Comparator.comparing(Board::getCreateDate).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), findAllBoard.size());

        if (start >= findAllBoard.size()) {
            return Page.empty(pageable);
        }

        List<Board> pagedBoards = findAllBoard.subList(start, end);

        return PageableExecutionUtils.getPage(
                pagedBoards,
                pageable,
                () -> findAllBoard.size());
    }

    public Optional<Board> findById(String qnaId) {
        return  data.stream()
                .filter(item -> item.getId().equals(qnaId))
                .findAny();
    }


    // fake repository helper

    /**
     * 몽고디비 pk 값이 String 형태인 걸 감안하여 구현
     */
    private String generateTestId() {
        return "test-board-" + ++idCounter;  // 예측 가능한 테스트용 ID 생성
    }

}
