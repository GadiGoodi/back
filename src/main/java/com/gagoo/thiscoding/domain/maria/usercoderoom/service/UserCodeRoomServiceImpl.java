package com.gagoo.thiscoding.domain.maria.usercoderoom.service;


import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.exception.OverCapacityException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PagingProcessor;
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

    public UserCodeRoom getUserCodeRoomByCodeRoomAndUserEmail(Long codeRoomId, String userEmail) {
        return userCodeRoomRepository.findByCodeRoomIdAndUserEmail(codeRoomId, userEmail)
            .orElseThrow(() -> new UserCodeRoomNotFoundException(
                ErrorCode.USER_CODE_ROOM_NOT_FOUND
            ));
    }

    /**
     * 초대된 코드방 인원수 체크
     */
    public void validateCapacity(CodeRoom codeRoom) {
        final int MAX_CAPACITY = 6;
        if (codeRoom.getHeadCount() >= MAX_CAPACITY) {
            throw new OverCapacityException(ErrorCode.OVER_CAPACITY_CODE_ROOM);
        }
    }

    /**
     * 코드방 참여 생성
     * @param userId
     * @param roomId
     * @return 생성한 UserCodeRoom
     */
    @Override
    public boolean createUserCodeRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );

        CodeRoom codeRoom = codeRoomRepository.findById(roomId).orElseThrow(
            () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );

        UserCodeRoom userCodeRoom = UserCodeRoom.create(user, codeRoom);

        if(userCodeRoomRepository.save(userCodeRoom) != null) {
            return true;
        }

        return false;
    }

    /**
     * 초대된 코드방 전체 조회
     */
    @Override
    public Page<InviteCodeRoom> getUserCodeRooms(int page) {
        Pageable pageable = PagingProcessor.getPageable(page, 12);
        return userCodeRoomRepository
            .findByEmail(SecurityUtils.getUserEmail(), pageable)
            .map(InviteCodeRoom::from);
    }

    /**
     * 초대된 코드방 수락
     *
    @Override
    @Transactional
    public void acceptCodeRoom(Long codeRoomId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUserEmail(codeRoomId, SecurityUtils.getUserEmail());
        validateCapacity(userCodeRoom.getCodeRoom());
        userCodeRoomRepository.save(userCodeRoom.accept());
    }

    /**
     * 초대된 코드방 거절
     */
    @Override
    @Transactional
    public void cancelCodeRoom(Long codeRoomId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUserEmail(codeRoomId, SecurityUtils.getUserEmail());
        userCodeRoomRepository.delete(userCodeRoom);
    }
}
