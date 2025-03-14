package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.CodeNotFoundException;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class CodeRoomServiceTest {
    private CodeRoomService codeRoomService;
    private CodeRoom testCodeRoom;
    private UserCodeRoom testUserCodeRoom;
    private Code testCode;
    private User testUser;
    private UuidHolder uuidHolder;

    @BeforeEach
    void init() {
        User user = User.builder()
                .id(1L)
                .email("test01@test.com")
                .password("encoded-password")
                .nickname("test01")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();

        TestContainer testContainer = TestContainer.builder()
                .securityUtils(new FakeSecurityUtils(user.getEmail()))
                .build();

        this.testUser = testContainer.userRepository.save(user);

        this.codeRoomService = testContainer.codeRoomService;
        this.uuidHolder = testContainer.uuidHolder;

        CodeRoom codeRoom = CodeRoom.create(CodeRoomCreate.builder()
                        .title("teach me")
                        .content("how to print the hello world?")
                        .language("java")
                        .build(),
                uuidHolder);

        this.testCodeRoom = testContainer.codeRoomRepository.save(codeRoom);

        UserCodeRoom userCodeRoom = UserCodeRoom.builder()
                .id(1L)
                .user(testUser)
                .codeRoom(testCodeRoom)
                .isActivated(false)
                .build();

        this.testUserCodeRoom = testContainer.userCodeRoomRepository.save(userCodeRoom);

        Code code = Code.create(CodeCreate.builder()
                .id("test-code-id")
                .roomId(1L)
                .value("/* Java */\\n\\nclass Main {\\n\\tstatic public void main(String []args) {\\n\\t\\tSystem.out.println(\"Hello, World!\");\\n\\t}\\n}")
                .fileName("main")
                .build(), 1L);

        this.testCode = testContainer.codeRepository.save(code);
    }

    @Nested
    @DisplayName("코드방 생성")
    class CodeRoomCreateUnitTest {
        @Test
        @DisplayName("코드방을 생성할 수 있다.")
        void createCodeRoom_코드방_생성_성공() {
            // given
            CodeRoomCreate codeRoomCreate = CodeRoomCreate.builder()
                    .title("teach me")
                    .content("how to print the hello world?")
                    .language("java")
                    .build();

            // when
            CodeRoom createdCodeRoom = codeRoomService.createCodeRoom(codeRoomCreate);

            // then
            assertThat(createdCodeRoom.getTitle()).isEqualTo(codeRoomCreate.getTitle());
            assertThat(createdCodeRoom.getContent()).isEqualTo(codeRoomCreate.getContent());
            assertThat(createdCodeRoom.getLanguage()).isEqualTo(codeRoomCreate.getLanguage());
        }
    }

    @Nested
    @DisplayName("코드방 조회")
    class CodeRoomGetUnitTest {
        @Test
        @DisplayName("특정 코드방을 조회할 수 있다.")
        void enterCodeRoom_코드방_조회_성공() {
            // when
            CodeRoomEnter codeRoomEnter = codeRoomService.enterCodeRoom(testCodeRoom.getUuid());

            // then
            assertThat(codeRoomEnter.getRoomId()).isEqualTo(testCodeRoom.getId());
            assertThat(codeRoomEnter.getLanguage()).isEqualTo(testCodeRoom.getLanguage());
            assertThat(codeRoomEnter.getValue()).isEqualTo(testCode.getValue());
        }

        @Test
        @DisplayName("존재하지 않는 코드방의 조회 에러가 발생한다.")
        void enterCodeRoom_존재하지_않는_코드방_조회() {
            // when & then
            Assertions.assertThrows(CodeRoomNotFoundException.class,
                    () -> codeRoomService.enterCodeRoom("invalid-uuid"));
        }

        @Test
        @DisplayName("로그인하지 않은 사용자는 코드방을 조회할 수 없다.")
        void enterCodeRoom_로그인하지_않은_사용자_코드방_조회() {
            // given
            TestContainer testContainer = TestContainer.builder()
                    .securityUtils(new FakeSecurityUtils(null))
                    .build();

            CodeRoomService unauthenticatedCodeRoomService = testContainer.codeRoomService;

            // when & then
            Assertions.assertThrows(AuthorizationException.class,
                    () -> unauthenticatedCodeRoomService.enterCodeRoom("invalid-uuid"));
        }
    }

    @Nested
    @DisplayName("코드방 코드 조회")
    class CodeRoomCodeGetUnitTest {
        @Test
        @DisplayName("특정 코드방의 특정 코드를 조회할 수 있다.")
        void getCodeByRoomIdAndFileName_특정_코드방_특정_코드_조회_성공() {
            // given
            Long id = 1L;
            String fileName = "main";

            // when
            Code newCode = codeRoomService.getCodeByRoomIdAndFileName(id, fileName);

            // then
            assertThat(newCode.getId()).isEqualTo(testCode.getId());
            assertThat(newCode.getRoomId()).isEqualTo(testCode.getRoomId());
            assertThat(newCode.getWriterId()).isEqualTo(testCode.getWriterId());
            assertThat(newCode.getFileName()).isEqualTo(testCode.getFileName());
            assertThat(newCode.getValue()).isEqualTo(testCode.getValue());
            assertThat(newCode.getSaveDate()).isEqualTo(testCode.getSaveDate());
        }

        @Test
        @DisplayName("존재하지 않는 코드방의 특정 코드 조회 에러가 발생한다.")
        void getCodeByRoomIdAndFileName_존재하지_않는_코드방_특정_코드_조회() {
            // given
            Long id = 0L;
            String fileName = "main";

            // when & then
            Assertions.assertThrows(CodeNotFoundException.class,
                    () -> codeRoomService.getCodeByRoomIdAndFileName(id, fileName));
        }

        @Test
        @DisplayName("특정 코드방의 존재하지 않는 코드 조회 에러가 발생한다.")
        void getCodeByRoomIdAndFileName_특정_코드방_존재하지_않는_코드_조회() {
            // given
            Long id = 1L;
            String fileName = "invalid-file-name";

            // when & then
            Assertions.assertThrows(CodeNotFoundException.class,
                    () -> codeRoomService.getCodeByRoomIdAndFileName(id, fileName));
        }

        @Test
        @DisplayName("존재하지 않는 코드방의 존재하지 않는 코드 조회 에러가 발생한다.")
        void getCodeByRoomIdAndFileName_존재하지_않는_코드방_존재하지_않는_코드_조회() {
            // given
            Long id = 0L;
            String fileName = "invalid-file-name";

            // when & then
            Assertions.assertThrows(CodeNotFoundException.class,
                    () -> codeRoomService.getCodeByRoomIdAndFileName(id, fileName));
        }
    }

}
