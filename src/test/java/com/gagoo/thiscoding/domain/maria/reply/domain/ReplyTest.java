package com.gagoo.thiscoding.domain.maria.reply.domain;

import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Reply 순수 도메인 비지니스 로직 테스트")
class ReplyTest {

    @Nested
    @DisplayName("Reply 작성 테스트")
    class CreateReplyTest {
        @Test
        @DisplayName("Reply를 생성할 수 있다")
        void User_정보와_ReplyCreate_로_댓글은_작성할_수_있다() {
            // given
            String qnaId = "test-qna-id";
            String content = "테스트 댓글";

            User user = User.builder()
                    .id(1L)
                    .email("junsj1230@naver.com")
                    .password("encoded-password")
                    .nickname("TestUser")
                    .imageUrl("test-image-url")
                    .isActivated(true)
                    .isBanned(false)
                    .build();

            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content(content)
                    .build();

            // when
            Reply reply = Reply.create(user, qnaId, replyCreate,null);

            // then
            assertThat(reply.getQnaId()).isEqualTo(qnaId);
            assertThat(reply.getUser()).isEqualTo(user);
            assertThat(reply.getContent()).isEqualTo(content);
            assertThat(reply.getParent()).isNull();
            assertThat(reply.isBlinded()).isFalse();
            assertThat(reply.getCreateDate()).isNull();
        }

        @Test
        @DisplayName("parentId 없이 Reply를 생성할 수 있다")
        void 부모_댓글이_없어도_댓글_작성이_가능하다() {

            // given
            String qnaId = "test-qna-id";
            String content = "테스트 댓글";

            User user = User.builder()
                    .id(1L)
                    .email("junsj1230@naver.com")
                    .password("encoded-password")
                    .nickname("TestUser")
                    .imageUrl("test-image-url")
                    .isActivated(true)
                    .isBanned(false)
                    .build();
            ;
            ReplyCreate replyCreate = ReplyCreate.builder()
                    .content(content)
                    .build();

            Reply reply = Reply.create(user, qnaId, replyCreate,null);

            // then
            assertThat(reply.getParent()).isNull();
            assertThat(reply.getContent()).isEqualTo(content);
            assertThat(reply.isBlinded()).isFalse();
        }
    }
}
