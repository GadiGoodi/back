package com.gagoo.thiscoding.domain.mongo.code.service.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CodeRepository {
    Code save(Code code);
    boolean existsByRoomIdAndFileName(Long roomId, String fileName);
    boolean existsByRoomId(Long roomId);
    Optional<Code> findById(String id);
    Optional<Code> findByRoomIdAndFileName(Long roomId, String fileName);
    Page<Code> findByRoomId(Long roomId, Pageable pageable);
}
