package com.gagoo.thiscoding.domain.maria.alarm.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Alarm {

    private Long id;
    private User sender;
    private User receiver;
    private Long targetId;
    private AlarmType type;
    private boolean isRead;
    private LocalDateTime createDate;

}
