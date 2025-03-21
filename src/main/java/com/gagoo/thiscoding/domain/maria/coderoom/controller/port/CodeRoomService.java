package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;

public interface CodeRoomService {
    CodeRoom createCodeRoom(CodeRoomCreate codeRoomCreate);
    CodeRoomEnter enterCodeRoom(String uuid);
}
