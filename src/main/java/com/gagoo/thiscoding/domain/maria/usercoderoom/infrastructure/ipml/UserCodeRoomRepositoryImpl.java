package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.ipml;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa.UserCodeRoomJpaRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import java.util.Optional;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCodeRoomRepositoryImpl implements UserCodeRoomRepository {
    private final UserCodeRoomJpaRepository userCodeRoomJpaRepository;

    @Override
    public UserCodeRoom getById(Long codeRoomId) {
        return findById(codeRoomId).orElseThrow(
                () -> new UserCodeRoomNotFoundException(ErrorCode.USER_CODE_ROOM_NOT_FOUND)
        );
    }

    @Override
    public Optional<UserCodeRoom> findById(Long id) {
        return userCodeRoomJpaRepository.findById(id).map(UserCodeRoomEntity::toModel);
    }

    @Override
    public UserCodeRoom save(UserCodeRoom userCodeRoom) {
        return userCodeRoomJpaRepository.save(UserCodeRoomEntity.from(userCodeRoom)).toModel();
    }

    @Override
    public void deleteById(Long userCodeRoomId) {
        userCodeRoomJpaRepository.deleteById(userCodeRoomId);
    }

    @Override
    public boolean existByCodeRoomIdAndUserId(Long codeRoomId, Long userId){
        return userCodeRoomJpaRepository.existsByCodeRoomIdAndUserId(codeRoomId, userId);
    }
}

