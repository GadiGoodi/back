package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.ParticipationService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception.NotUserCodeRoomParticipantException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipationServiceTest {
    public ParticipationService participationService;
    public UserCodeRoomService userCodeRoomService;
    public User testUser;
    public CodeRoom testCodeRoom1;
    public CodeRoom testCodeRoom2;
    public UserCodeRoom testUserCodeRoom1;
    public UserCodeRoom testUserCodeRoom2;
    private UuidHolder uuidHolder;

    @BeforeEach
    void init() {
        User user1 = User.builder()
                .id(1L)
                .email("test01@test.com")
                .password("encoded-password")
                .nickname("test01")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("test02@test.com")
                .password("encoded-password")
                .nickname("test02")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        TestContainer testContainer = TestContainer.builder()
                .securityUtils(new FakeSecurityUtils(user1.getEmail()))
                .build();

        this.testUser = testContainer.userRepository.save(user1);

        this.uuidHolder = testContainer.uuidHolder;

        CodeRoom codeRoom1 = CodeRoom.create(CodeRoomCreate.builder()
                        .title("teach me")
                        .content("how to print the hello world?")
                        .language("java")
                        .build(),
                uuidHolder);

        CodeRoom codeRoom2 = CodeRoom.create(CodeRoomCreate.builder()
                        .title("I'll teach you")
                        .content("print the hello world.")
                        .language("java")
                        .build(),
                uuidHolder);

        this.testCodeRoom1 = testContainer.codeRoomRepository.save(codeRoom1);
        this.testCodeRoom2 = testContainer.codeRoomRepository.save(codeRoom2);

        UserCodeRoom userCodeRoom1 = UserCodeRoom.builder()
                .id(1L)
                .user(testUser)
                .codeRoom(testCodeRoom1)
                .isActivated(false)
                .build();

        UserCodeRoom userCodeRoom2 = UserCodeRoom.builder()
                .id(2L)
                .user(user2)
                .codeRoom(testCodeRoom2)
                .isActivated(false)
                .build();

        this.testUserCodeRoom1 = testContainer.userCodeRoomRepository.save(userCodeRoom1);
        this.testUserCodeRoom2 = testContainer.userCodeRoomRepository.save(userCodeRoom2);

        this.participationService = testContainer.participationService;

        this.userCodeRoomService = testContainer.userCodeRoomService;
    }

    @Nested
    @DisplayName("(참여 중인) 코드방 입/퇴장")
    class UserCodeRoomPostUnitTest {
        @Test
        @DisplayName("참여 중인 코드방에 입퇴장할 수 있다.")
        void accessUserCodeRoom_코드방_입퇴장_성공() {
            // given
            Long id = 1L;

            // when & then
            assertThat(participationService.accessUserCodeRoom(id)).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 코드방에 입퇴장 시 에러가 발생한다.")
        void accessUserCodeRoom_존재하지_않는_코드방_입퇴장() {
            // given
            Long id = 3L;

            // when & then
            Assertions.assertThrows(UserCodeRoomNotFoundException.class,
                    () -> participationService.accessUserCodeRoom(id));
        }

        @Test
        @DisplayName("참여하지 않은 코드방에 입퇴장 시 에러가 발생한다.")
        void accessUserCodeRoom_참여하지_않은_코드방_입퇴장() {
            // given
            Long id = 2L;

            // when & then
            Assertions.assertThrows(NotUserCodeRoomParticipantException.class,
                    () -> participationService.accessUserCodeRoom(id));
        }

        @Test
        @DisplayName("로그인하지 않은 사용자는 코드방에 입퇴장할 수 없다.")
        void accessUserCodeRoom_로그인하지_않은_사용자_코드방_입장() {
            // given
            User user1 = User.builder()
                    .id(1L)
                    .email("test01@test.com")
                    .password("encoded-password")
                    .nickname("test01")
                    .imageUrl("test-image-url")
                    .isActivated(true)
                    .isBanned(false)
                    .build();

            TestContainer testContainer = TestContainer.builder()
                    .securityUtils(new FakeSecurityUtils(null))
                    .build();

            testContainer.userRepository.save(user1);

            CodeRoom codeRoom1 = CodeRoom.create(CodeRoomCreate.builder()
                            .title("teach me")
                            .content("how to print the hello world?")
                            .language("java")
                            .build(),
                    uuidHolder);

            testContainer.codeRoomRepository.save(codeRoom1);

            UserCodeRoom userCodeRoom1 = UserCodeRoom.builder()
                    .id(1L)
                    .user(testUser)
                    .codeRoom(testCodeRoom1)
                    .isActivated(false)
                    .build();

            testContainer.userCodeRoomRepository.save(userCodeRoom1);

            ParticipationService unauthenticatedParticipationService = testContainer.participationService;

            Long id = 1L;

            // when & then
            Assertions.assertThrows(AuthorizationException.class,
                    () -> unauthenticatedParticipationService.accessUserCodeRoom(id));
        }
    }

    @Nested
    @DisplayName("(참여 중인) 코드방 탈퇴")
    class UserCodeRoomDeleteUnitTest {
        @Test
        @DisplayName("참여 중인 코드방을 탈퇴할 수 있다.")
        void leaveUserCodeRoom_코드방_탈퇴_성공() {
            // given
            Long id = 1L;

            // when & then
            assertThat(participationService.leaveUserCodeRoom(id)).isTrue();

        }

        @Test
        @DisplayName("존재하지 않는 참여 코드방 탈퇴 시 에러가 발생한다.")
        void leaveUserCodeRoom_존재하지_않는_참여_코드방_탈퇴() {
            // given
            Long id = 3L;

            // when & then
            Assertions.assertThrows(UserCodeRoomNotFoundException.class,
                    () -> participationService.leaveUserCodeRoom(id));
        }

        @Test
        @DisplayName("참여하지 않은 코드방 탈퇴 시 에러가 발생한다.")
        void leaveUserCodeRoom_참여하지_않은_코드방_탈퇴() {
            // given
            Long id = 2L;

            // when & then
            Assertions.assertThrows(NotUserCodeRoomParticipantException.class,
                    () -> participationService.leaveUserCodeRoom(id));
        }

        @Test
        @DisplayName("로그인하지 않은 사용자는 코드방을 탈퇴할 수 없다.")
        void leaveUserCodeRoom_로그인하지_않은_사용자_코드방_탈퇴() {
            // given
            User user1 = User.builder()
                    .id(1L)
                    .email("test01@test.com")
                    .password("encoded-password")
                    .nickname("test01")
                    .imageUrl("test-image-url")
                    .isActivated(true)
                    .isBanned(false)
                    .build();

            TestContainer testContainer = TestContainer.builder()
                    .securityUtils(new FakeSecurityUtils(null))
                    .build();

            testContainer.userRepository.save(user1);

            CodeRoom codeRoom1 = CodeRoom.create(CodeRoomCreate.builder()
                            .title("teach me")
                            .content("how to print the hello world?")
                            .language("java")
                            .build(),
                    uuidHolder);

            testContainer.codeRoomRepository.save(codeRoom1);

            UserCodeRoom userCodeRoom1 = UserCodeRoom.builder()
                    .id(1L)
                    .user(testUser)
                    .codeRoom(testCodeRoom1)
                    .isActivated(false)
                    .build();

            testContainer.userCodeRoomRepository.save(userCodeRoom1);

            ParticipationService unauthenticatedParticipationService = testContainer.participationService;

            Long id = 1L;

            // when & then
            Assertions.assertThrows(AuthorizationException.class,
                    () -> unauthenticatedParticipationService.leaveUserCodeRoom(id));
        }
    }
}
