package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.CodeNotFoundException;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class CodeRoomServiceImpl implements CodeRoomService {

    private final UserRepository userRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final CodeRoomRepository codeRoomRepository;
    private final CodeRepository codeRepository;
    private final SecurityUtils securityUtils;
    private final UuidHolder uuidHolder;

    /**
     * 코드방 생성
     * @param codeRoomCreate
     * @return 생성한 CodeRoom
     */
    @Override
    public CodeRoom createCodeRoom(CodeRoomCreate codeRoomCreate) {
        CodeRoom codeRoom = CodeRoom.create(codeRoomCreate, uuidHolder);

        return codeRoomRepository.save(codeRoom);
    }

    /**
     * 코드방 입장
     * @param uuid
     * @return 입장한 CodeRoom
     */
    @Override
    public CodeRoomEnter enterCodeRoom(String uuid) {
        User currentUser = getCurrentUser();
        CodeRoom codeRoom = getCodeRoomByUuid(uuid);
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomIdAndUserId(codeRoom.getId(), currentUser.getId());

        accessUserCodeRoom(userCodeRoom);

        Code code = getCodeByRoomIdAndFileName(codeRoom.getId(), "main");

        return CodeRoomEnter.enterCodeRoom(codeRoom.getId(), codeRoom.getLanguage(), code.getValue(), code.getId());
    }

    /**
     * 코드 조회
     * @param roomId
     * @param fileName
     * @return 조회한 Code
     */
    private Code getCodeByRoomIdAndFileName(Long roomId, String fileName) {
        return codeRepository.findByRoomIdAndFileName(roomId, fileName).orElseThrow(
                () -> new CodeNotFoundException(ErrorCode.CODE_NOT_FOUND)
        );
    }

    /**
     * 코드방 조회
     * @param uuid
     * @return 코드방
     */
    private CodeRoom getCodeRoomByUuid(String uuid) {
        return codeRoomRepository.findByUuid(uuid).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );
    }

    /**
     * 참여 코드방 조회
     * @param codeRoomId
     * @param userId
     * @return 참여 코드방
     */
    private UserCodeRoom getUserCodeRoomByCodeRoomIdAndUserId(Long codeRoomId, Long userId) {
        return userCodeRoomRepository.findByCodeRoomIdAndUserId(codeRoomId, userId).orElseThrow(
                () -> new UserCodeRoomNotFoundException(ErrorCode.USER_CODE_ROOM_NOT_FOUND)
        );
    }

    /**
     * 참여 코드방 내 isActivated 속성 활성화
     * @param userCodeRoom
     */
    private void accessUserCodeRoom(UserCodeRoom userCodeRoom) {
        if(!userCodeRoom.isActivated()) {
            userCodeRoomRepository.save(userCodeRoom.access());
        }
    }

    /**
     * 로그인 사용자 조회
     * @return 로그인 사용자
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }
}
