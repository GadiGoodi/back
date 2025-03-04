package com.gagoo.thiscoding.domain.maria.coderoom.service;

import static com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType.CODE;
import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MAX_CAPACITY;
import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MIN_CAPACITY;
import static com.gagoo.thiscoding.global.paging.PageSize.CODEROOM;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.alarm.service.exception.AlarmNotFoundException;
import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.InvitationCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.AlreadyJoinedCodeRoomException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.exception.CapacityOutOfBoundsException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final CodeRoomRepository codeRoomRepository;
    private final AlarmRepository alarmRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final SecurityUtils securityUtils;

    /**
     * 초대된 코드방 조회
     */
    @Override
    public CustomPageDto<InvitationCodeRoomResponse> getInvitationCodeRoomList(Pageable pageable) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), CODEROOM);
        return CustomPageDto.of(
            codeRoomRepository.findInvitedCodeRoomsByUser(currentUser, customPageable)
                .map(InvitationCodeRoomResponse::from)
        );
    }

    /**
     * 초대된 코드방 수락
     */
    @Transactional
    @Override
    public UserCodeRoom acceptInvitationCodeRoom(Long codeRoomId, Long alarmId) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        validateCodeRoom(codeRoomId, alarmId, currentUser.getId());

        CodeRoom codeRoom = getByCodeRoomId(codeRoomId);
        codeRoom.join();

        UserCodeRoom userCodeRoom = UserCodeRoom.create(currentUser, codeRoom);

        codeRoomRepository.save(codeRoom);
        alarmRepository.deleteById(alarmId);

        return userCodeRoomRepository.save(userCodeRoom);
    }

    /**
     * 초대된 코드방 거절
     */
    @Transactional
    @Override
    public void rejectInvitationCodeRoom(Long codeRoomId, Long alarmId) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        validateCodeRoom(codeRoomId, alarmId, currentUser.getId());
        alarmRepository.deleteById(alarmId);
    }

    /**
     * 코드룸 수락, 거절에 필요한 공통 검증 로직
     */
    private void validateCodeRoom(Long codeRoomId, Long alarmId, Long userId) {
        try {
            getByCodeRoomId(codeRoomId);
            validateCodeRoomAlarm(codeRoomId, alarmId);
            validateJoinCodeRoom(codeRoomId, userId);
            validateCapacity(codeRoomId);
        } catch (RuntimeException e) {
            alarmRepository.deleteById(alarmId);
            throw e;
        }
    }

    /**
     * 코드룸 인원수 검증
     */
    private void validateCapacity(Long codeRoomId) {
        int currentHeadCount = getByCodeRoomId(codeRoomId).getHeadCount();

        if(!(currentHeadCount >= MIN_CAPACITY && currentHeadCount < MAX_CAPACITY)){
            throw new CapacityOutOfBoundsException(ErrorCode.CAPACITY_CODE_ROOM);
        }
    }

    /**
     * 코드룸 알람 유효 검증
     * */
    private void validateCodeRoomAlarm(Long codeRoomId, Long alarmId) {
        if(!alarmRepository.existsByIdAndTypeAndTargetId(alarmId, CODE,
            codeRoomId)){
            throw new AlarmNotFoundException(ErrorCode.ALARM_NOT_FOUND);
        }
    }

    /**
     * 이미 참여한 코드룸인지 검증
     * */
    private void validateJoinCodeRoom(Long codeRoomId, Long userId) {
        if(userCodeRoomRepository.existByCodeRoomIdAndUserId(codeRoomId,
            userId)){
            throw new AlreadyJoinedCodeRoomException(ErrorCode.ALREADY_CODE_ROOM);
        }
    }

    private CodeRoom getByCodeRoomId(Long codeRoomId) {
        return codeRoomRepository.findById(codeRoomId)
            .orElseThrow(() -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND));
    }
}
