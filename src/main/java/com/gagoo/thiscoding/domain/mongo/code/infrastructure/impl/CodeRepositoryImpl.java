package com.gagoo.thiscoding.domain.mongo.code.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo.CodeMongoRepository;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {
    private final CodeMongoRepository codeMongoRepository;

    @Override
    public Code save(Code code) {
        return codeMongoRepository.save(CodeDocument.from(code)).toModel();
    }
}
