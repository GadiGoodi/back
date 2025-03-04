package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.Impl;

import com.gagoo.thiscoding.domain.maria.alarm.domain.AlarmType;
import com.gagoo.thiscoding.domain.maria.alarm.infrastructure.QAlarmEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.QCodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.QUserEntity;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitationCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.QCodeRoomEntity.codeRoomEntity;
import static com.gagoo.thiscoding.domain.maria.user.infrastructure.QUserEntity.userEntity;
import static com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.QUserCodeRoomEntity.userCodeRoomEntity;


@Repository
@RequiredArgsConstructor
public class CodeRoomCustomRepositoryImpl implements CodeRoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<InvitationCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable) {
        QCodeRoomEntity codeRoomEntity = QCodeRoomEntity.codeRoomEntity;
        QAlarmEntity alarmEntity = QAlarmEntity.alarmEntity;
        QUserEntity userEntity = QUserEntity.userEntity;

        List<InvitationCodeRoom> results = queryFactory
            .select(Projections.constructor(InvitationCodeRoom.class,
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
                .and(alarmEntity.type.eq(AlarmType.CODE))
                .and(codeRoomEntity.headCount.goe(1))
                .and(codeRoomEntity.headCount.lt(6)))
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

    public List<User> findUserListByUserCodeRoom(UserCodeRoom userCodeRoom) {

        return queryFactory.select(Projections.constructor(User.class,
                        userEntity.id,
                        userEntity.email,
                        userEntity.password,
                        userEntity.nickname,
                        userEntity.imageUrl,
                        userEntity.isActivated,
                        userEntity.isBanned,
                        userEntity.role,
                        userEntity.social
                ))
                .from(userEntity)
                .join(userCodeRoomEntity).on(userEntity.id.eq(userCodeRoom.getUser().getId()))
                .where(userCodeRoomEntity.isActivated.isTrue()
                        .and(userCodeRoomEntity.codeRoom.id.eq(userCodeRoom.getCodeRoom().getId())))
                .fetch();
    }

    public Page<UserCodeRoom> findAllByEmailAndIsActivatedTrue(String email, Pageable pageable) {
        BooleanExpression emailCondition = userEntity.email.eq(email);
        BooleanExpression isActivatedCondition = userCodeRoomEntity.isActivated.isTrue();

        List<UserCodeRoom> result = queryFactory
                .selectFrom(userCodeRoomEntity)
                .join(userCodeRoomEntity.codeRoom, codeRoomEntity).fetchJoin()
                .join(userCodeRoomEntity.user, userEntity).fetchJoin()
                .where(emailCondition.and(isActivatedCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(UserCodeRoomEntity::toModel)
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(userCodeRoomEntity.count())
                .from(userCodeRoomEntity)
                .join(userCodeRoomEntity.codeRoom, codeRoomEntity)
                .join(userCodeRoomEntity.user, userEntity)
                .where(emailCondition.and(isActivatedCondition));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}


