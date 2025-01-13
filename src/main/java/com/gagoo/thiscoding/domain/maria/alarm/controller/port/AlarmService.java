package com.gagoo.thiscoding.domain.maria.alarm.controller.port;

import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;

public interface AlarmService {

    Alarm findById(Long alarmId);
    Alarm save(Long alarmId);
}
