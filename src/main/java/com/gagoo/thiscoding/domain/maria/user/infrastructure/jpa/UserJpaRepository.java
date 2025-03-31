package com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);

    Page<UserEntity> findByNicknameContainingAndRoleAndIsActivatedTrueAndIsBannedFalseAndIdNot(
        String nickname, Role role, Long excludeId, Pageable pageable);

}
