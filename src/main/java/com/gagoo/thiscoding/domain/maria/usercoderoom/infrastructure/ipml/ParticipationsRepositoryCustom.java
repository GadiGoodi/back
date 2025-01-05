package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.ipml;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.QCodeRoomEntity.codeRoomEntity;
import static com.gagoo.thiscoding.domain.maria.user.infrastructure.QUserEntity.userEntity;
import static com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.QUserCodeRoomEntity.userCodeRoomEntity;

@Repository
@RequiredArgsConstructor
public class ParticipationsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

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

//        long total = queryFactory
//                .selectFrom(userCodeRoomEntity)
//                .join(userCodeRoomEntity.codeRoom, codeRoomEntity)
//                .join(userCodeRoomEntity.user, userEntity)
//                .where(emailCondition.and(isActivatedCondition))
//                .fetchCount();

        JPAQuery<Long> countQuery = queryFactory
                .select(userCodeRoomEntity.count())
                .from(userCodeRoomEntity)
                .join(userCodeRoomEntity.codeRoom, codeRoomEntity)
                .join(userCodeRoomEntity.user, userEntity)
                .where(emailCondition.and(isActivatedCondition));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}
