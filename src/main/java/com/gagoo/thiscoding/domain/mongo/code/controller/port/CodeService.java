package com.gagoo.thiscoding.domain.mongo.code.controller.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import reactor.core.publisher.Mono;

public interface CodeService {
    Mono<Code> createCode(CodeCreate codeCreate);
}
