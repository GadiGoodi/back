package com.gagoo.thiscoding.domain.maria.usercoderoom.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.AlreadyJoinedCodeRoomException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
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

    private final CodeRoomRepository codeRoomRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final UserFinder userFinder;
    private final SecurityUtils securityUtils;

    /**
     * 코드방 참여 생성
     * @param roomId
     * @return 생성한 UserCodeRoom
     */
    @Override
    public boolean createUserCodeRoom(Long roomId) {
        User currentUser = getCurrentUser();

        CodeRoom codeRoom = getCodeRoom(roomId);

        validateJoinCodeRoom(roomId, currentUser.getId());

        UserCodeRoom userCodeRoom = UserCodeRoom.create(currentUser, codeRoom);

        if(userCodeRoomRepository.save(userCodeRoom) != null) {
            return true;
        }

        return false;
    }

    /**
     * 이미 참여한 코드방인지 검증
     * @param roomId
     * @param userId
     */
    private void validateJoinCodeRoom(Long roomId, Long userId) {
        if(userCodeRoomRepository.existByCodeRoomIdAndUserId(roomId, userId)) {
            throw new AlreadyJoinedCodeRoomException(ErrorCode.ALREADY_CODE_ROOM);
        }
    }

    /**
     * 로그인 사용자 조회
     */
    private User getCurrentUser() {
        return userFinder.getByEmail(securityUtils.getUserEmail());
    }

    private CodeRoom getCodeRoom(Long roomId) {
        return codeRoomRepository.findById(roomId).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );
    }

}
