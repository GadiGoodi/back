package com.gagoo.thiscoding.domain.maria.usercoderoom.service;


import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
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
}
