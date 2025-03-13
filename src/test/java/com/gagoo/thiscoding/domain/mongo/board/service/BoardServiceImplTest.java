package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BoardServiceImplTest {

    public BoardService boardService;
    public User testUser;
    public Board testBoard;

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

        this.boardService = testContainer.boardService;
        this.testUser = testContainer.userRepository.save(user);

        Board board = Board.create(testUser, BoardCreate.of("testTitle1, ", "testContent1", "Java"));

        this.testBoard = testContainer.boardRepository.save(board);

    }

    @Test
    void getMyPagePostQnA() {

        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Board> result = boardService.getMyPagePostQnA(pageable);

        //then
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("testTitle1");
        assertThat(result.getContent().get(0).getContent()).isEqualTo("testContent1");
        assertThat(result.getContent().get(0).getUserId()).isEqualTo(1L);
    }
}