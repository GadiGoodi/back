package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodeRoomServiceImpl implements CodeRoomService {

    private final CodeRoomRepository codeRoomRepository;

    // 코드방 생성
    // return 생성된 codeRoom 정보
    @Override
    public CodeRoom createCodeRoom(CodeRoomCreate codeRoomCreate) {
        CodeRoom codeRoom = CodeRoom.create(codeRoomCreate);
        
        return codeRoomRepository.save(codeRoom);
    }

}
