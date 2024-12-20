package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.ipml;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa.UserCodeRoomJpaRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCodeRoomRepositoryImpl implements UserCodeRoomRepository {
    private final UserCodeRoomJpaRepository userCodeRoomJpaRepository;

    @Override
    public Page<UserCodeRoom> findByUserId(Long userId, Pageable pageable) {
        return userCodeRoomJpaRepository.findAllByUserIdAndIsAcceptedFalse(userId, pageable)
            .map(UserCodeRoomEntity::toModel);
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
    public void delete(UserCodeRoom userCodeRoom) {
        userCodeRoomJpaRepository.delete(UserCodeRoomEntity.from(userCodeRoom));
    }

    @Override
    public Optional<UserCodeRoom> findByCodeRoomIdAndUserId(Long codeRoomId, Long userId) {
        return userCodeRoomJpaRepository.findByCodeRoomIdAndUserId(codeRoomId, userId).map(UserCodeRoomEntity::toModel);
    }
}
