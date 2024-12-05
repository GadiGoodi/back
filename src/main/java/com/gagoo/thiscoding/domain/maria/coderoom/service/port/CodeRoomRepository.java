package com.gagoo.thiscoding.domain.maria.coderoom.service.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;

import java.util.Optional;
import java.util.UUID;

public interface CodeRoomRepository {
    CodeRoom save(CodeRoom codeRoom);
    Optional<CodeRoom> findByUuid(UUID uuid);
    Optional<CodeRoom> findById(Long roomId);
}
