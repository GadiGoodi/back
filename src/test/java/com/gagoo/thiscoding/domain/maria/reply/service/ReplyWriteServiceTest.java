package com.gagoo.thiscoding.domain.maria.reply.service;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.exception.ReplyNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("댓글 생성 단위 테스트")
class ReplyWriteServiceTest {

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

        Board board = Board.create(testUser, BoardCreate.of(
                "테스트 게시물",
                "테스트 게시물 내용",
                "Java")
        );
        this.testBoard = testContainer.boardRepository.save(board);

    }

    @Nested
    @DisplayName("댓글 작성")
    class ReplyServiceCreateUnitTest {

        @Test
        @DisplayName("게시물에 댓글을 남길 수 있다")
        void create_댓글_작성_성공() {
            // given
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

        @Test
        @DisplayName("게시물이 없을 때 댓글 작성 시 에러가 발생한다")
        void create_존재하지_않는_게시물에_댓글_작성() {
            // given
            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content("테스트 댓글")
                    .parentId(null)
                    .build();

            // when & then
            assertThrows(QnaNotFoundException.class,
                    () -> replyService.create("wrong-id", replyCreate)
            );
        }

        @Test
        @DisplayName("존재하지 않는 부모 댓글 ID로 대댓글 작성 시 예외가 발생한다")
        void create_존재하지_않는_댓글에_대댓글_작성() {
            // given
            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content("자식 댓글")
                    .parentId(99999999L) // 존재하지 않는 부모 댓글 ID
                    .build();

            // when & then
            assertThrows(ReplyNotFoundException.class,
                    () -> replyService.create(testBoard.getId(), replyCreate));
        }

        @Test
        @DisplayName("로그인 하지 않은 유저는 댓글을 생성할 수 없다")
        void create_로그인_하지_않은_사용자_댓글_작성() {
            // given
            TestContainer testContainer = TestContainer.builder()
                    .securityUtils(new FakeSecurityUtils(null)) // null 사용자
                    .build();

            ReplyService unauthenticatedReplyService = testContainer.replyService;

            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content("테스트 댓글")
                    .parentId(null)
                    .build();

            // when & then
            assertThrows(AuthorizationException.class,
                    () -> unauthenticatedReplyService.create(testBoard.getId(), replyCreate)
            );
        }
    }
}