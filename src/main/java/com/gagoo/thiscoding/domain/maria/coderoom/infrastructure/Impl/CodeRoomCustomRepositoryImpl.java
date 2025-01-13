package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.Impl;

import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.QAlarmEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.QCodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.QUserEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class CodeRoomCustomRepositoryImpl implements CodeRoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<InvitedCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable) {
        QCodeRoomEntity codeRoomEntity = QCodeRoomEntity.codeRoomEntity;
        QAlarmEntity alarmEntity = QAlarmEntity.alarmEntity;
        QUserEntity userEntity = QUserEntity.userEntity;

        List<InvitedCodeRoom> results = queryFactory
            .select(Projections.constructor(InvitedCodeRoom.class,
                alarmEntity.id,
                codeRoomEntity.id,
                codeRoomEntity.title,
                codeRoomEntity.content,
                codeRoomEntity.headCount,
                codeRoomEntity.language,
                userEntity.nickname,
                userEntity.imageUrl
            ))
            .from(alarmEntity)
            .join(codeRoomEntity).on(alarmEntity.targetId.eq(codeRoomEntity.id))
            .join(userEntity).on(alarmEntity.sender.id.eq(userEntity.id))
            .where(alarmEntity.receiver.id.eq(user.getId())
                .and(alarmEntity.type.eq(AlarmType.CODE)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(codeRoomEntity.count())
            .from(alarmEntity)
            .join(codeRoomEntity).on(alarmEntity.targetId.eq(codeRoomEntity.id))
            .join(userEntity).on(alarmEntity.sender.id.eq(userEntity.id))
            .where(alarmEntity.receiver.id.eq(user.getId())
                .and(alarmEntity.type.eq(AlarmType.CODE)));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}


