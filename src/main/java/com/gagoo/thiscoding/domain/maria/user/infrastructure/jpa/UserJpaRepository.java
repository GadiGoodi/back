package com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
