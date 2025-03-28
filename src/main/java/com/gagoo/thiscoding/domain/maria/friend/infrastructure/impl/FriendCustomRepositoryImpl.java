package com.gagoo.thiscoding.domain.maria.friend.infrastructure.impl;

import static com.gagoo.thiscoding.domain.maria.friend.infrastructure.QFriendEntity.friendEntity;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.FriendEntity;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa.FriendCustomRepository;
import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendCustomRepositoryImpl implements FriendCustomRepository {

    private final JPAQueryFactory query;

    /**
     * 내 친구목록 조회
     * */
    @Override
    public Page<FriendInfo> findMyFriends(String nickname, Pageable pageable) {

        List<FriendInfo> results = query
            .select(Projections.constructor(FriendInfo.class,
                friendEntity.id,
                friendNickname(nickname),
                friendImageUrl(nickname)
            ))
            .from(friendEntity)
            .where(MyFriend(nickname))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(friendEntity.count())
            .from(friendEntity)
            .where(MyFriend(nickname));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    /**
     * 내가 보낸 친구요청 조회
     * */
    @Override
    public Page<FriendInfo> findSentFriendRequests(String nickname, Pageable pageable) {
        List<FriendInfo> results = query
            .select(Projections.constructor(FriendInfo.class,
                friendEntity.id,
                friendEntity.receiver.nickname,
                friendEntity.receiver.imageUrl
            ))
            .from(friendEntity)
            .where(senderNicknameEq(nickname))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(friendEntity.count())
            .from(friendEntity)
            .where(senderNicknameEq(nickname));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    /**
     * 내가 받은 친구요청 조회
     * */
    @Override
    public Page<FriendInfo> findReceivedFriendRequests(String nickname, Pageable pageable) {
        List<FriendInfo> results = query
            .select(Projections.constructor(FriendInfo.class,
                friendEntity.id,
                friendEntity.sender.nickname,
                friendEntity.sender.imageUrl
            ))
            .from(friendEntity)
            .where(receiverNicknameEq(nickname))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(friendEntity.count())
            .from(friendEntity)
            .where(receiverNicknameEq(nickname));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    /**
     * 내 친구목록에 존재하는 친구를 keyword로 검색
     * */
    @Override
    public Page<FriendInfo> searchMyFriends(String currentUser, String keyword, Pageable pageable) {
        List<FriendInfo> results = query
            .select(Projections.constructor(FriendInfo.class,
                friendEntity.id,
                friendNickname(currentUser),
                friendImageUrl(currentUser)
            ))
            .from(friendEntity)
            .where(MyFriend(currentUser).and(friendsContainsNickname(keyword)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(friendEntity.count())
            .from(friendEntity)
            .where(MyFriend(currentUser).and(friendsContainsNickname(keyword)));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    /**
     * target 유저가 나와 친구인지 확인 후 객체 반환
     * */
    public Optional<Friend> findMyFriend(Long myId, Long targetId) {
        FriendEntity result = query
            .selectFrom(friendEntity)
            .join(friendEntity.sender)
            .join(friendEntity.receiver)
            .where(validFriend(myId, targetId))
            .fetchOne();

        return Optional.ofNullable(result)
            .map(FriendEntity::toModel);
    }

    private Expression<String> friendNickname(String nickname) {
        return Expressions.cases()
            .when(friendEntity.sender.nickname.eq(nickname))
            .then(friendEntity.receiver.nickname)
            .otherwise(friendEntity.sender.nickname);
    }

    private Expression<String> friendImageUrl(String nickname) {
        return Expressions.cases()
            .when(friendEntity.sender.nickname.eq(nickname))
            .then(friendEntity.receiver.imageUrl)
            .otherwise(friendEntity.sender.imageUrl);
    }

    /**
     * 해당 키워드로 friend 테이블의 nickname과 일치하는게 존재하는지 확인하는 조건식
     * */
    private BooleanExpression friendsContainsNickname(String keyword) {
        if (keyword == null) {
            return null;
        }
        return friendEntity.sender.nickname.containsIgnoreCase(keyword)
            .or(friendEntity.receiver.nickname.containsIgnoreCase(keyword));
    }

    /**
     * 상대방과 내가 친구인지 확인하는 조건식
     * */
    private BooleanExpression validFriend(Long myId, Long targetId) {
        return friendEntity.sender.id.eq(myId)
            .and(friendEntity.receiver.id.eq(targetId))
            .or(
                friendEntity.sender.id.eq(targetId)
                    .and(friendEntity.receiver.id.eq(myId))
            );
    }

    /**
     * 해당 닉네임으로 친구조회 조건식
     * */
    private BooleanExpression MyFriend(String nickname) {
        return friendEntity.isFriend.isTrue()
            .and(friendEntity.sender.nickname.eq(nickname)
                .or(friendEntity.receiver.nickname.eq(nickname)));
    }

    /**
     *  해당 nickname으로 보낸 친구요청 조회 조건식
     * */
    private BooleanExpression senderNicknameEq(String nickname) {
        return friendEntity.sender.nickname.eq(nickname)
            .and(friendEntity.isFriend.isFalse());
    }

    /**
     * 해당 nickname으로 받은 친구요청 조회 조건식
     * */
    private BooleanExpression receiverNicknameEq(String nickname) {
        return friendEntity.receiver.nickname.eq(nickname)
            .and(friendEntity.isFriend.isFalse());
    }

}
