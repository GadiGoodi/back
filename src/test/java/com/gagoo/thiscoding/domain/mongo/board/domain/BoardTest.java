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

        BoardCreate boardCreate = BoardCreate.builder()
                .title("테스트 게시물")
                .content("테스트 게시물 내용")
                .language("Java")
                .parentId(null)
                .build();

        // when
        Board board = Board.create(user, boardCreate);

        // then
        assertThat(board.getUserId()).isEqualTo(user.getId());
        assertThat(board.getNickname()).isEqualTo(user.getNickname());
        assertThat(board.getProfileImg()).isEqualTo(user.getImageUrl());

        assertThat(board.getTitle()).isEqualTo(boardCreate.getTitle());
        assertThat(board.getContent()).isEqualTo(boardCreate.getContent());
        assertThat(board.getLanguage()).isEqualTo(boardCreate.getLanguage());
        assertThat(board.getParentId()).isEqualTo(boardCreate.getParentId());

        assertThat(board.getLikeCount()).isEqualTo(0L);
        assertThat(board.getViewCount()).isEqualTo(0L);
        assertThat(board.getAnswerCount()).isEqualTo(0L);
        assertThat(board.isBlind()).isEqualTo(false);
        assertThat(board.isSelected()).isEqualTo(false);
    }
}