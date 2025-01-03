package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.ipml;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.UserCodeRoomEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
//        if (userCodeRoom == null || userCodeRoom.getUser() == null || userCodeRoom.getCodeRoom() == null) {
//            throw new IllegalArgumentException("UserCodeRoom, User, or CodeRoom cannot be null");
//        }

        // Query 실행
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

        long total = queryFactory
                .selectFrom(userCodeRoomEntity)
                .join(userCodeRoomEntity.codeRoom, codeRoomEntity)
                .join(userCodeRoomEntity.user, userEntity)
                .where(emailCondition.and(isActivatedCondition))
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }
}
