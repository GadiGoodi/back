package com.gagoo.thiscoding.domain.mongo.board.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("게시판 순수 도메인 테스트")
class BoardTest {

    @Test
    @DisplayName("Board를 생성할 수 있다")
    void User_정보와_BoardCreate_객체로_게시판을_작성할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("junsj1230@naver.com")
                .password("encoded-password")
                .nickname("TestUser")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        BoardCreate boardCreate = BoardCreate.of("테스트 게시물", "테스트 게시물 내용", "Java");

        // when
        Board board = Board.create(user, boardCreate);

        // then
        assertThat(board.getUserId()).isEqualTo(user.getId());
        assertThat(board.getNickname()).isEqualTo(user.getNickname());
        assertThat(board.getProfileImg()).isEqualTo(user.getImageUrl());

        assertThat(board.getTitle()).isEqualTo(boardCreate.title());
        assertThat(board.getContent()).isEqualTo(boardCreate.content());
        assertThat(board.getLanguage()).isEqualTo(boardCreate.language());

        assertThat(board.getLikeCount()).isEqualTo(0L);
        assertThat(board.getViewCount()).isEqualTo(0L);
        assertThat(board.getAnswerCount()).isEqualTo(0L);
        assertThat(board.getParentId()).isEqualTo("root");
        assertThat(board.isBlind()).isEqualTo(false);
        assertThat(board.isSelected()).isEqualTo(false);
    }
}