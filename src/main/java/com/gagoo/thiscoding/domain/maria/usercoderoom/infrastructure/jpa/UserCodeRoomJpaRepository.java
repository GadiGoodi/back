package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCodeRoomJpaRepository extends JpaRepository<UserCodeRoomEntity, Long> {
    Optional<UserCodeRoomEntity> findById(Long id);

    @Query("SELECT ucr FROM UserCodeRoomEntity ucr " +
        "JOIN FETCH ucr.codeRoom " +
        "JOIN FETCH ucr.user " +
        "WHERE ucr.user.email = :email AND ucr.isActivated = false")
    Page<UserCodeRoomEntity> findAllByEmailAndIsAcceptedFalse(@Param("email") String email, Pageable pageable);

    @Query("SELECT ucr FROM UserCodeRoomEntity ucr " +
        "JOIN FETCH ucr.user " +
        "JOIN FETCH ucr.codeRoom " +
        "WHERE ucr.codeRoom.id = :codeRoomId AND ucr.user.email = :email")
    Optional<UserCodeRoomEntity> findByCodeRoomIdAndUserEmail(
        @Param("codeRoomId") Long codeRoomId,
        @Param("email") String email
    );
}

