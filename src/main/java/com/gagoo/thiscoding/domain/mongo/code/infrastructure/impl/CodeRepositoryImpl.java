package com.gagoo.thiscoding.domain.mongo.code.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo.CodeMongoRepository;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {
    private final CodeMongoRepository codeMongoRepository;

    @Override
    public Code save(Code code) {
        return codeMongoRepository.save(CodeDocument.from(code)).toModel();
    }

    @Override
    public boolean existsByRoomIdAndFileName(Long roomId, String fileName) {
        return codeMongoRepository.existsByRoomIdAndFileName(roomId, fileName);
    }

    @Override
    public Optional<Code> findById(String id) {
        return codeMongoRepository.findById(id).map(CodeDocument::toModel);
    }

    @Override
    public Optional<Code> findByRoomIdAndFileName(Long roomId, String fileName) {
        return codeMongoRepository.findByRoomIdAndFileName(roomId, fileName).map(CodeDocument::toModel);
    }

}
