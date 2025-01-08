package com.gagoo.thiscoding.domain.maria.reply.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.reply.infrastructure.ReplyEntity;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.impl.ReplyCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity, Long> {
}
