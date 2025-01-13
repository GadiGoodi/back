package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCodeRoomJpaRepository extends JpaRepository<UserCodeRoomEntity, Long> {
}

