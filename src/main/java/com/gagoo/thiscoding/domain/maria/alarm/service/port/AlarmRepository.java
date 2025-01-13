package com.gagoo.thiscoding.domain.maria.alarm.service.port;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import java.util.Optional;

public interface AlarmRepository {

    Optional<Alarm> findById(Long id);
    Alarm save(Alarm alarm);
    void delete(Alarm alarm);
}
