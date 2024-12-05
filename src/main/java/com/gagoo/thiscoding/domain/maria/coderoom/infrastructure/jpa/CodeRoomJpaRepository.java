package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.CodeRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CodeRoomJpaRepository extends JpaRepository<CodeRoomEntity, Long> {
}
