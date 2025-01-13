package com.gagoo.thiscoding.domain.maria.alarm.service;

import com.gagoo.thiscoding.domain.maria.alarm.controller.port.AlarmService;
import com.gagoo.thiscoding.domain.maria.alarm.domain.Alarm;
import com.gagoo.thiscoding.domain.maria.alarm.service.exception.AlarmNotFoundException;
import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    @Override
    public Alarm findById(Long alarmId) {
       return alarmRepository.findById(alarmId).orElseThrow(() -> new AlarmNotFoundException(
            ErrorCode.ALARM_NOT_FOUND));
    }

    @Override
    public Alarm save(Long alarmId) {
        return alarmRepository.save(findById(alarmId));
    }
}
