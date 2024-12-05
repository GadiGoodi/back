package com.gagoo.thiscoding.domain.mongo.code.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo.CodeMongoRepository;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {
    private final CodeMongoRepository codeMongoRepository;

    @Override
    public Mono<Code> save(Code code) {
        return Mono.from(codeMongoRepository.save(CodeDocument.from(code))).map(CodeDocument::toModel);
    }
}
