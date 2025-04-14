package com.gagoo.thiscoding.domain.mongo.code.controller.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeService {
    Code createCode(CodeCreate codeCreate);
    Code saveCode(CodeCreate codeCreate);
    Code getById(String codeId);
    Page<CodeList> getCodeList(Long roomId, Pageable pageable);
}
