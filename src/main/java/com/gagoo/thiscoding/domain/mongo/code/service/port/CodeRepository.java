package com.gagoo.thiscoding.domain.mongo.code.service.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;

import java.util.Optional;

public interface CodeRepository {
    Code save(Code code);
    boolean existsByRoomIdAndFileName(Long roomId, String fileName);
    Optional<Code> findById(String id);
    Optional<Code> findByRoomIdAndFileName(Long roomId, String fileName);
}
