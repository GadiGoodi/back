package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCodeRoomJpaRepository extends JpaRepository<UserCodeRoomEntity, Long> {
    boolean existsByCodeRoomIdAndUserId(Long codeRoomId, Long userId);
    Optional<UserCodeRoomEntity> findByCodeRoomIdAndUserId(Long codeRoomId, Long userId);
}

