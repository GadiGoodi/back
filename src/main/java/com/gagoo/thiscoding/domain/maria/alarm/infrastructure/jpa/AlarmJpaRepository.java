package com.gagoo.thiscoding.domain.maria.alarm.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {
}
