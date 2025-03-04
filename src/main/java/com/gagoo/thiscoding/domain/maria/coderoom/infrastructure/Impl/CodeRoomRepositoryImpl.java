package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.Impl;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitationCodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.CodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomJpaRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CodeRoomRepositoryImpl implements CodeRoomRepository {

    private final CodeRoomJpaRepository codeRoomJpaRepository;
    private final CodeRoomCustomRepository codeRoomCustomRepository;

    @Override
    public CodeRoom save(CodeRoom codeRoom) {
        return codeRoomJpaRepository.save(CodeRoomEntity.from(codeRoom)).toModel();
    }

    @Override
    public Optional<CodeRoom> findByUuid(String uuid) {
        return codeRoomJpaRepository.findByUuid(uuid).map(CodeRoomEntity::toModel);
    }

    @Override
    public Optional<CodeRoom> findById(Long id) {
        return codeRoomJpaRepository.findById(id).map(CodeRoomEntity::toModel);
    }

    @Override
    public Page<InvitationCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable) {
        return codeRoomCustomRepository.findInvitedCodeRoomsByUser(user, pageable);
    }

    @Override
    public void deleteById(Long id) {
        codeRoomJpaRepository.deleteById(id);
    }

}
