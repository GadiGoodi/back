package com.gagoo.thiscoding.domain.mongo.code.controller.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;

public interface CodeService {
    Code createCode(CodeCreate codeCreate);
    Code saveCode(CodeCreate codeCreate);
    Code getById(String codeId);
    void validateRoomIdAndFileNameExists(Long roomId, String fileName);
}
