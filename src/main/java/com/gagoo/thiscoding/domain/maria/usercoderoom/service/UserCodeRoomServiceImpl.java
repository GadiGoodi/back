package com.gagoo.thiscoding.domain.maria.usercoderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Builder
@RequiredArgsConstructor
public class UserCodeRoomServiceImpl implements UserCodeRoomService {

    private final UserRepository userRepository;
    private final CodeRoomRepository codeRoomRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;

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

}
