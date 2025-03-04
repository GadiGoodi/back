package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardReadServiceImplTest {

    private BoardService boardService;
    private Board testBoard;

    @BeforeEach
    void init() {
        User user = User.builder()
                .email("junsj1230@naver.com")
                .password("encoded-password")
                .nickname("Liverpool")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        TestContainer testContainer = TestContainer.builder()
                .securityUtils(new FakeSecurityUtils(user.getEmail()))
                .build();
        testContainer.userRepository.save(user);

        this.boardService = testContainer.boardService;

        Board board = Board.create(user, BoardCreate.of(
                "테스트 게시물",
                "테스트 게시물 내용",
                "Java")
        );

        this.testBoard = testContainer.boardRepository.save(board);
    }

    @Nested
    @DisplayName("게시물 상세조회 단위테스트")
    class getQnaTest {

        @Test
        @DisplayName("게시물 상세조회 성공테스트")
        void qnaId로_게시물_조회를_할_수_있다() {
            // given
            String qnaId = testBoard.getId();

            // when
            QnaDetail qnaDetail = boardService.get(qnaId);

            // then
            assertThat(qnaDetail.getTitle()).isEqualTo(testBoard.getTitle());
            assertThat(qnaDetail.getContent()).isEqualTo(testBoard.getContent());
            assertThat(qnaDetail.getLanguage()).isEqualTo(testBoard.getLanguage());
            assertThat(qnaDetail.getReplyCount()).isEqualTo(0L);
        }

        @Test
        @DisplayName("게시물 상세조회 실패테스트")
        void 잘못된_qnaId로_조회하면_에러가_발생한다() {
            // given
            String qnaId = "wrong-qna-id";

            // when & then
            assertThrows(QnaNotFoundException.class,
                    () -> boardService.get(qnaId)
            );
        }
    }

    @Nested
    @DisplayName("게시물 전체조회 단위테스트")
    class findAllQnaTest {

        @BeforeEach
        void init() {
            for (int i = 1; i <= 27; i++) {
                boardService.create(BoardCreate.of(
                        "테스트 게시물" + i,
                        "테스트 게시물 내용" + i,
                        "Java" + i)
                );
            }
        }

        @Test
        @DisplayName("게시물 전체조회 성공 테스트")
        void 게시물_전체_조회시_페이지네이션이_정상동작_한다() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            // when
            CustomPageDto<QnaList> result = boardService.findAll(pageable);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).isNotEmpty();
            assertThat(result.getTotalElements()).isEqualTo(28); // 초기 1개 + 추가 27개
            assertThat(result.getTotalPage()).isEqualTo(3);
            assertThat(result.getContent().size()).isLessThanOrEqualTo(10);
        }

        @Test
        @DisplayName("게시물 최신순 정렬 확인")
        void 최신순_정렬_확인() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            // when
            CustomPageDto<QnaList> result = boardService.findAll(pageable);

            // then
            LocalDateTime prevCreateDate = null;
            for (QnaList board : result.getContent()) {
                if (prevCreateDate != null) {
                    assertThat(board.createDate()).isBeforeOrEqualTo(prevCreateDate);
                }
                prevCreateDate = board.createDate();
            }
        }

    }
}