package com.gagoo.thiscoding.domain.maria.alarm.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
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

    @Builder
    public Alarm(Long id, User sender, User receiver, Long targetId, AlarmType type, boolean isRead,
        LocalDateTime createDate) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.targetId = targetId;
        this.type = type;
        this.isRead = isRead;
        this.createDate = createDate;
    }
}
