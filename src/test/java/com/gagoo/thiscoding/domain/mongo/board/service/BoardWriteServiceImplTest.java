package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardWriteServiceImplTest {

    public BoardService boardService;
    public User testUser;

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

        this.testUser = testContainer.userRepository.save(user);
        this.boardService = testContainer.boardService;
    }

    @Test
    @DisplayName("게시물 작성 단위테스트")
    void BoardCreate_request_로_게시물을_작성할_수_있다() {
        // given
        BoardCreate boardCreate = BoardCreate.of("테스트 게시물", "테스트 게시물 내용", "Java");

        // when
        Board board = boardService.create(boardCreate);
        System.out.println("board.toString() = " + board.toString());

        // then
        assertThat(board.getTitle()).isEqualTo(boardCreate.title());
        assertThat(board.getContent()).isEqualTo(boardCreate.content());
        assertThat(board.getLanguage()).isEqualTo(boardCreate.language());
        assertThat(board.getParentId()).isEqualTo("root");
    }
}