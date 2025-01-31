package com.gagoo.thiscoding.domain.maria.coderoom.service.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;

import java.util.Optional;

public interface CodeRoomRepository {
    CodeRoom save(CodeRoom codeRoom);
    Optional<CodeRoom> findByUuid(String uuid);
    Optional<CodeRoom> findById(Long id);
    void deleteById(Long id);
}
