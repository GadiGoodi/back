package com.gagoo.thiscoding.domain.maria.alarm.infrastructure.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.AlarmEntity;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.jpa.AlarmJpaRepository;
import com.gagoo.thiscoding.domain.maria.alarm.service.exception.AlarmNotFoundException;
import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public Alarm getById(Long alarmId) {
        return alarmJpaRepository.findById(alarmId).map(AlarmEntity::toModel)
            .orElseThrow(() -> new AlarmNotFoundException(
                ErrorCode.ALARM_NOT_FOUND));
    }

    @Override
    public Optional<Alarm> findById(Long alarmId) {
        return alarmJpaRepository.findById(alarmId).map(AlarmEntity::toModel);
    }

    @Override
    public Alarm save(Alarm alarm) {
        return alarmJpaRepository.save(AlarmEntity.from(alarm)).toModel();
    }

    @Transactional(propagation = REQUIRES_NEW)
    @Override
    public void deleteById(Long alarmId) {
        alarmJpaRepository.deleteById(alarmId);
    }

    @Override
    public boolean existsByIdAndTypeAndTargetId(Long alarmId, AlarmType alarmType, Long targetId) {
        return alarmJpaRepository.existsByIdAndTypeAndTargetId(alarmId, alarmType, targetId);
    }
}
