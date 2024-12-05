package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.Impl;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.CodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomJpaRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CodeRoomRepositoryImpl implements CodeRoomRepository {

    private final CodeRoomJpaRepository codeRoomJpaRepository;

    @Override
    public CodeRoom save(CodeRoom codeRoom) {
        return codeRoomJpaRepository.save(CodeRoomEntity.from(codeRoom)).toModel();
    }
}
