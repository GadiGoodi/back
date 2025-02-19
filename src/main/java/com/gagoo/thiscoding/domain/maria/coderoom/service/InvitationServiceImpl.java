package com.gagoo.thiscoding.domain.maria.coderoom.service;

import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MAX_CAPACITY;
import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MIN_CAPACITY;
import static com.gagoo.thiscoding.global.paging.PageSize.CODEROOM;
import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.service.exception.AlarmNotFoundException;
import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.exception.CapacityOutOfBoundsException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final CodeRoomRepository codeRoomRepository;
    private final AlarmRepository alarmRepository;
    private final CodeRoomCustomRepository codeRoomCustomRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final SecurityUtils securityUtils;

    /**
     * 초대된 코드방 조회
     */
    @Override
    public Page<InvitedCodeRoom> findInvitedCodeRoomsByUser(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), CODEROOM);
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());

        return codeRoomCustomRepository.findInvitedCodeRoomsByUser(
                currentUser,
                customPageable
            );
    }

    /**
     * 초대된 코드방 수락
     */
    @Transactional
    public void acceptCodeRoom(Long alarmId, Long codeRoomId) {
        if (!validateCapacity(codeRoomId)) {
            alarmRepository.delete(getByAlarmId(alarmId));
            throw new CapacityOutOfBoundsException(ErrorCode.CAPACITY_CODE_ROOM);
        }

        CodeRoom codeRoom = getByCodeRoomId(codeRoomId);
        codeRoom.join();

        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());

        UserCodeRoom userCodeRoom = UserCodeRoom.create(currentUser, codeRoom);

        codeRoomRepository.save(codeRoom);
        userCodeRoomRepository.save(userCodeRoom);
        alarmRepository.delete(getByAlarmId(alarmId));
    }

    /**
     * 초대된 코드방 거절
     */
    public void cancelCodeRoom(Long alarmId, Long codeRoomId) {
        CodeRoom codeRoom = codeRoomRepository.findById(codeRoomId).orElse(null);

        alarmRepository.delete(getByAlarmId(alarmId));

        if (codeRoom == null) {
            throw new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND);
        }
    }

    /**
     * 코드방 인원수 체크
     */
    public boolean validateCapacity(Long codeRoomId) {
        int currentHeadCount = getByCodeRoomId(codeRoomId).getHeadCount();
        return currentHeadCount >= MIN_CAPACITY && currentHeadCount <= MAX_CAPACITY;
    }

    public CodeRoom getByCodeRoomId(Long codeRoomId) {
        return codeRoomRepository.findById(codeRoomId)
                .orElseThrow(() -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND));
    }

    public Alarm getByAlarmId(Long alarmId) {
        return alarmRepository.findById(alarmId)
                .orElseThrow(() -> new AlarmNotFoundException(ErrorCode.ALARM_NOT_FOUND));
    }
}
