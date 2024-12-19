package com.gagoo.thiscoding.domain.maria.usercoderoom.service;


import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.controller.response.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.UserCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.usercoderoom.exception.OverCapacityException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.pageDto.CustomPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCodeRoomServiceImpl implements UserCodeRoomService {
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final UserRepository userRepository;
    private final CodeRoomRepository codeRoomRepository;

    // 코드방 참여 생성
    @Override
    public UserCodeRoom createUserCodeRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );

        CodeRoom codeRoom = codeRoomRepository.findById(roomId).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );

        UserCodeRoom userCodeRoom = UserCodeRoom.create(user, codeRoom);
        return userCodeRoomRepository.save(userCodeRoom);
    }

    public UserCodeRoom getUserCodeRoomByCodeRoomAndUser(Long codeRoomId, Long userId) {
        return userCodeRoomRepository.findByCodeRoomIdAndUserId(codeRoomId, userId).orElseThrow(() -> new UserCodeRoomNotFoundException(
            ErrorCode.USER_CODE_ROOM_NOT_FOUND
        ));
    }

    public void validateCapacity(CodeRoom codeRoom) {
        final int MAX_CAPACITY = 6;
        if(codeRoom.getHeadCount() >= MAX_CAPACITY){
            throw new OverCapacityException(ErrorCode.OVER_CAPACITY_CODE_ROOM);
        }
    }

    /**
     * 초대된 코드방 전체 조회
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public CustomPageDto<InviteCodeRoom> getUserCodeRooms(Long userId, Pageable pageable) {
        Page<InviteCodeRoom> codeRooms = userCodeRoomRepository.findByUserId(userId, pageable)
            .map(InviteCodeRoom::from);
        return CustomPageDto.from(codeRooms);
    }

    /**
     * 초대된 코드방 수락
     * @param codeRoomId, userId
     * @return
     */
    @Override
    @Transactional
    public UserCodeRoomResponse acceptCodeRoom(Long codeRoomId, Long userId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUser(codeRoomId, userId);
        validateCapacity(userCodeRoom.getCodeRoom());
        userCodeRoom.accept();
        userCodeRoomRepository.save(userCodeRoom);
        return new UserCodeRoomResponse(userCodeRoom.getId(), "수락 완료");
    }

    /**
     * 초대된 코드방 거절
     * @param codeRoomId, userId
     */
    @Override
    @Transactional
    public UserCodeRoomResponse cancelCodeRoom(Long codeRoomId, Long userId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUser(codeRoomId, userId);
        userCodeRoomRepository.delete(userCodeRoom);
        return new UserCodeRoomResponse(userCodeRoom.getId(), "거절 완료");
    }
}
