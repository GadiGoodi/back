package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewService;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardViewServiceImplTest {

    private User testUser;
    private BoardViewService boardViewService;
    private BoardRepository boardRepository;

    @BeforeEach
    void init() {
        User user = User.builder()
                .id(1L)
                .email("junsj1230@naver.com")
                .password("encoded-password")
                .nickname("TestUser")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        TestContainer testContainer = TestContainer.builder()
                .securityUtils(new FakeSecurityUtils(user.getEmail()))
                .build();

        this.testUser = testContainer.userRepository.save(user);
        this.boardViewService = testContainer.boardViewService;
        this.boardRepository = testContainer.boardRepository;
    }

    @Nested
    @DisplayName("조회수 증가 테스트")
    class processVisit {

        @Test
        @DisplayName("처음 방문할 시 조회수 증가")
        void 오늘_방문한_적_없는_사용자는_조회수가_증가한다() {
            // Given
            Board board = Board.create(testUser, BoardCreate.of("testTitle1, ", "testContent1", "Java"));
            Board saveBoard = boardRepository.save(board);

            // When
            boardViewService.processVisit(saveBoard.getId(), testUser.getEmail());

            // Then
            Board findBoard = boardRepository.getById(saveBoard.getId());
            Assertions.assertThat(findBoard.getViewCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("처음 방문할 시 조회수 증가")
        void 오늘_방문한_적_기록이_있는_사용자는_조회수가_증가하지_않는다() {
            Board board = Board.create(testUser, BoardCreate.of("testTitle1, ", "testContent1", "Java"));
            Board saveBoard = boardRepository.save(board);

            // When
            boardViewService.processVisit(saveBoard.getId(), testUser.getEmail());
            boardViewService.processVisit(saveBoard.getId(), testUser.getEmail());

            // Then
            Board findBoard = boardRepository.getById(saveBoard.getId());
            Assertions.assertThat(findBoard.getViewCount()).isEqualTo(1);
        }
    }


}