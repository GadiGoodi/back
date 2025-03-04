package com.gagoo.thiscoding.domain.maria.alarm.service.port;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import java.util.Optional;

public interface AlarmRepository {

    Alarm getById(Long alarmId);
    Optional<Alarm> findById(Long id);
    Alarm save(Alarm alarm);
    void deleteById(Long alarmId);
    boolean existsByIdAndTypeAndTargetId(Long alarmId, AlarmType alarmType, Long targetId);
}
