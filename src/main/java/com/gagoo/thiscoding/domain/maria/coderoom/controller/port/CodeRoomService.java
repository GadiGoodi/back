package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;

import java.util.UUID;

public interface CodeRoomService {
    CodeRoom createCodeRoom(CodeRoomCreate codeRoomCreate);
}
