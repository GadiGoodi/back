package com.gagoo.thiscoding.domain.mongo.code.controller.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;

public interface CodeService {
    Code createCode(CodeCreate codeCreate);
}
