package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCodeRoomJpaRepository extends JpaRepository<UserCodeRoomEntity, Long> {
    Page<UserCodeRoomEntity> findAllByUser_IdAndIsAcceptedFalse(Long userId, Pageable pageable);

}
