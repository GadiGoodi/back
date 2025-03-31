package com.gagoo.thiscoding.domain.maria.coderoom.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import com.gagoo.thiscoding.domain.maria.alarm.service.exception.AlarmNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CapacityOutOfBoundsException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InvitationServiceWriteTest {

    public InvitationService invitationService;
    public User testReceiver;
    public User testSender;
    public Alarm testAlarm;
    public CodeRoom testCodeRoom;
    public TestContainer testContainer;

    @BeforeEach
    void init() {
        User receiver = User.builder()
            .id(1L)
            .email("test01@naver.com")
            .password("encoded-password")
            .nickname("testReceiver")
            .imageUrl("test-image-url1")
            .isActivated(true)
            .isBanned(false)
            .build();

        User sender = User.builder()
            .id(2L)
            .email("test02@naver.com")
            .password("encoded-password")
            .nickname("TestSender")
            .imageUrl("test-image-url2")
            .isActivated(true)
            .isBanned(false)
            .build();

        testContainer = TestContainer.builder()
            .securityUtils(new FakeSecurityUtils(receiver.getEmail()))
            .build();

        testReceiver = testContainer.userRepository.save(receiver);
        testSender = testContainer.userRepository.save(sender);

        invitationService = testContainer.invitationService;

        CodeRoom codeRoom = CodeRoom.builder()
            .title("CodeRoom Test")
            .content("CodeRoom Test")
            .uuid("1111-2222-3333-4444")
            .headCount(1)
            .language("Java")
            .build();

        testCodeRoom = testContainer.codeRoomRepository.save(codeRoom);

        Alarm alarm = Alarm.builder()
            .sender(testSender)
            .receiver(testReceiver)
            .targetId(testCodeRoom.getId())
            .type(AlarmType.CODE)
            .isRead(false)
            .createDate(LocalDateTime.now())
            .build();

        testAlarm = testContainer.alarmRepository.save(alarm);
    }

    @Nested
    @DisplayName("초대된 코드방 단위 테스트")
    class InvitationServiceUnitTest {

        @Test
        void 초대된_코드방을_수락하면_유저_코드룸이_생성된다() {
            // given
            Long alarmId = testAlarm.getId();
            Long codeRoomId = testCodeRoom.getId();

            // when
            UserCodeRoom userCodeRoom = invitationService.acceptInvitationCodeRoom(codeRoomId, alarmId);

            // then
            assertThat(userCodeRoom.getUser()).isEqualTo(testReceiver);
            assertThat(userCodeRoom.getCodeRoom()).isEqualTo(testCodeRoom);
            assertThat(userCodeRoom.getCodeRoom().getId()).isEqualTo(testAlarm.getTargetId());
        }

        @Test
        void 초대된_코드방을_거절하면_알람이_삭제되고_인원수는_유지된다() {
            // given
            Long alarmId = testAlarm.getId();
            Long codeRoomId = testCodeRoom.getId();
            int beforeHeadCount = testCodeRoom.getHeadCount();

            // when
            invitationService.rejectInvitationCodeRoom(codeRoomId, alarmId);

            // then
            assertThrows(AlarmNotFoundException.class, () -> testContainer.alarmRepository.getById(alarmId));  // 거절하면 알람이 삭제되어야 함
            assertThat(testCodeRoom.getHeadCount()).isEqualTo(beforeHeadCount);
        }

        @Test
        void 코드룸_인원이_유효범위를_벗어나면_초대_수락에_실패한다() {
            // given
            CodeRoom overCapacityCodeRoom = CodeRoom.builder()
                .headCount(6)
                .build();

            CodeRoom underCapacityCodeRoom = CodeRoom.builder()
                .headCount(0)
                .build();

            CodeRoom saveOverCapacityRoom = testContainer.codeRoomRepository.save(overCapacityCodeRoom);
            CodeRoom saveUnderCapacityRoom = testContainer.codeRoomRepository.save(underCapacityCodeRoom);

            Alarm overCapacityCodeRoomAlarm = Alarm.builder()
                .sender(testSender)
                .receiver(testReceiver)
                .targetId(saveOverCapacityRoom.getId())
                .type(AlarmType.CODE)
                .isRead(false)
                .createDate(LocalDateTime.now())
                .build();

            Alarm underCapacityCodeRoomAlarm = Alarm.builder()
                .sender(testSender)
                .receiver(testReceiver)
                .targetId(saveUnderCapacityRoom.getId())
                .type(AlarmType.CODE)
                .isRead(false)
                .createDate(LocalDateTime.now())
                .build();

            Alarm saveOverCapacityCodeRoomAlarm = testContainer.alarmRepository.save(overCapacityCodeRoomAlarm);
            Alarm saveUnderCapacityCodeRoomAlarm = testContainer.alarmRepository.save(underCapacityCodeRoomAlarm);

            // when & then
            assertThrows(CapacityOutOfBoundsException.class,
                () -> invitationService.acceptInvitationCodeRoom(saveOverCapacityRoom.getId(), saveOverCapacityCodeRoomAlarm.getId()));

            assertThrows(CapacityOutOfBoundsException.class,
                () -> invitationService.acceptInvitationCodeRoom(saveUnderCapacityRoom.getId(), saveUnderCapacityCodeRoomAlarm.getId()));
        }
    }
}
