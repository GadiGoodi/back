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
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());

        CodeRoom codeRoom = codeRoomRepository.findByUuid(uuid).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );


        UserCodeRoom userCodeRoom = userCodeRoomRepository.findByCodeRoomIdAndUserId(codeRoom.getId(), currentUser.getId()).orElseThrow(
                () -> new UserCodeRoomNotFoundException(ErrorCode.USER_CODE_ROOM_NOT_FOUND)
        );

        userCodeRoomRepository.save(userCodeRoom.access());

        Code code = getCodeByRoomIdAndFileName(codeRoom.getId(), "main");

        return CodeRoomEnter.enterCodeRoom(codeRoom.getId(), codeRoom.getLanguage(), code.getValue(), code.getId());
    }

    /**
     * 코드 조회
     * @param roomId
     * @param fileName
     * @return 조회한 Code
     */
    @Override
    public Code getCodeByRoomIdAndFileName(Long roomId, String fileName) {
        return codeRepository.findByRoomIdAndFileName(roomId, fileName).orElseThrow(
                () -> new CodeNotFoundException(ErrorCode.CODE_NOT_FOUND)
        );
    }

}
