package com.gagoo.thiscoding.domain.maria.alarm.infrastructure;

import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Alarm")
public class AlarmEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType type;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "is_read")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

}
