package com.gagoo.thiscoding.domain.maria.reply.service;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class ReplyReadServiceTest {

    public ReplyService replyService;
    public User testUser;
    public Board testBoard;

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
        this.replyService = testContainer.replyService;

        Board board = Board.create(testUser, BoardCreate.builder()
                .title("테스트 게시물")
                .content("테스트 게시물 내용")
                .language("Java")
                .parentId(null)
                .build());

        this.testBoard = testContainer.boardRepository.save(board);

        for (int i = 1; i <= 27; i++) {
            Reply reply = Reply.create(testUser, testBoard.getId(),
                    ReplyCreate.builder()
                            .content("테스트 댓글 " + i)
                            .parentId(null)
                            .build()
            );
            testContainer.replyRepository.save(reply);
        }
    }

    @Nested
    @DisplayName("게시물 댓글 조회")
    class ReplyListGetUnitTest {
        @Test
        @DisplayName("특정 게시물의 댓글을 페이징하여 조회할 수 있다")
        void getQnAReply_페이징_조회_성공() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            // when
            CustomPageDto<ReplyList> result = replyService.getQnAReply(testBoard.getId(), pageable);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).isNotEmpty();
            assertThat(result.getTotalElements()).isEqualTo(27);
            assertThat(result.getTotalPage()).isEqualTo(3);
            assertThat(result.getContent().size()).isLessThanOrEqualTo(10);
        }

        @Test
        @DisplayName("존재하지 않는 게시물의 댓글 조회 에러가 발생한다.")
        void getQnAReply_존재하지_않는_게시물_조회() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            // when & then
            Assertions.assertThrows(QnaNotFoundException.class,
                    () -> replyService.getQnAReply("non-existent-id", pageable)
            );
        }

        @Test
        @DisplayName("게시물의 댓글을 최신순으로 정렬하여 조회한다")
        void getQnAReply_최신순_정렬() {
            // given
            Pageable pageable = PageRequest.of(0, 10);

            // when
            CustomPageDto<ReplyList> result = replyService.getQnAReply(testBoard.getId(), pageable);

            // then
            LocalDateTime prevCreateDate = null;

            for (ReplyList reply : result.getContent()) {
                if (prevCreateDate != null) {
                    assertThat(reply.getCreateDate()).isBeforeOrEqualTo(prevCreateDate); // 이 전의 댓글보다 뒤에 정렬되어 있어야 테스트 통과
                }
                prevCreateDate = reply.getCreateDate();
            }
        }
    }
}
