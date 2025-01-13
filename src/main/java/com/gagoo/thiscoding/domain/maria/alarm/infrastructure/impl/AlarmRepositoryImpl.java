package com.gagoo.thiscoding.domain.maria.alarm.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.AlarmEntity;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.jpa.AlarmJpaRepository;
import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public Alarm save(Alarm alarm) {
        return alarmJpaRepository.save(AlarmEntity.from(alarm)).toModel();
    }

    @Override
    public Optional<Alarm> findById(Long alarmId) {
        return alarmJpaRepository.findById(alarmId).map(AlarmEntity::toModel);
    }

    @Override
    public void delete(Alarm alarm) {
        alarmJpaRepository.delete(AlarmEntity.from(alarm));
    }
}
