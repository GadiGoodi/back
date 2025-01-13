package com.gagoo.thiscoding.domain.maria.reply.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gagoo.thiscoding.domain.maria.reply.infrastructure.QReplyEntity.*;
import static com.gagoo.thiscoding.domain.maria.user.infrastructure.QUserEntity.*;

@Repository
@RequiredArgsConstructor
public class ReplyCustomRepository {

    private final JPAQueryFactory query;

    public Page<ReplyList> findByQnaId(String qnaId, Pageable pageable) {

        List<ReplyList> results = query.select(Projections.constructor(ReplyList.class,
                        replyEntity.id,
                        replyEntity.parentId,
                        userEntity.nickname,
                        userEntity.imageUrl,
                        replyEntity.content,
                        replyEntity.createDate))
                .from(replyEntity)
                .leftJoin(replyEntity.user, userEntity)
                .where(qnaIdEq(qnaId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(replyEntity.createDate.desc().nullsLast())
                .fetch();

        JPAQuery<Long> countQuery = query.select(replyEntity.count())
                .from(replyEntity)
                .where(qnaIdEq(qnaId));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    public void deleteRepliesAndParent(Reply reply) {
        query.delete(replyEntity)
            .where(replyEntity.parentId.eq(reply.getId())
                .or(replyEntity.id.eq(reply.getId())))
            .execute();
    }

    private BooleanExpression qnaIdEq(String qnaId) {
        return replyEntity.qnaId.eq(qnaId);
    }
}
