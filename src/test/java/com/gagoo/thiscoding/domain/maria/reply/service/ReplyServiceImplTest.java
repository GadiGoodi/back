package com.gagoo.thiscoding.domain.maria.reply.service;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ReplyService 단위 테스트")
class ReplyServiceImplTest {

    public ReplyService replyService;
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

        this.replyService = testContainer.replyService;
        this.testUser = testContainer.userRepository.save(user);

        Board board = Board.create(testUser, BoardCreate.builder()
                .title("테스트 게시물")
                .content("테스트 게시물 내용")
                .language("Java")
                .parentId(null)
                .build());

        this.testBoard = testContainer.boardRepository.save(board);

    }

    @Nested
    @DisplayName("댓글 작성 단위테스트")
    class ReplyServiceCreateUnitTest {

        @Test
        @DisplayName("게시물에 댓글을 남길 수 있다")
        void Board_Id_와_ReplyCreate_로_댓글을_생성할_수_있다() {
            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content("테스트 댓글")
                    .parentId(null)
                    .build();

            // when
            Reply createdReply = replyService.create(testBoard.getId(), replyCreate);

            // then
            assertThat(createdReply.getContent()).isEqualTo(replyCreate.getContent());
            assertThat(createdReply.getUser().getEmail()).isEqualTo(testUser.getEmail());
            assertThat(createdReply.getQnaId()).isEqualTo(testBoard.getId());
        }
    }
}