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
                        replyEntity.parent.id,
                        userEntity.nickname,
                        userEntity.imageUrl,
                        replyEntity.content,
                        replyEntity.createDate))
                .from(replyEntity)
                .leftJoin(replyEntity.user, userEntity)
                .leftJoin(replyEntity.parent)
                .where(qnaReply(qnaId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(replyEntity.createDate.desc().nullsLast())
                .fetch();

        JPAQuery<Long> countQuery = query.select(replyEntity.count())
                .from(replyEntity)
                .where(qnaIdEq(qnaId));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    public Page<ReplyList> findRepliesByParentId(String qnaId, Long parentId,Pageable pageable) {

        List<ReplyList> results = query.select(Projections.constructor(ReplyList.class,
                        replyEntity.id,
                        replyEntity.parent.id,
                        userEntity.nickname,
                        userEntity.imageUrl,
                        replyEntity.content,
                        replyEntity.createDate))
                .from(replyEntity)
                .leftJoin(replyEntity.user, userEntity)
                .leftJoin(replyEntity.parent)
                .where(qnaReplies(qnaId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(replyEntity.createDate.desc().nullsLast())
                .fetch();

        //대댓글 계산 쿼리
        JPAQuery<Long> countQuery = query.select(replyEntity.count())
                .from(replyEntity)
                .where(replyEntity.parent.id.eq(parentId));

    return PageableExecutionUtils.getPage(results,pageable, countQuery::fetchOne);
    }

    public void deleteRepliesAndParent(Reply reply) {
        query.delete(replyEntity)
            .where(replyEntity.parent.id.eq(reply.getId())
                .or(replyEntity.id.eq(reply.getId())))
            .execute();
    }
    private  BooleanExpression qnaReply(String qnaId) {
        return qnaIdEq(qnaId).and(parentIdIsNull());
    }
    private BooleanExpression qnaReplies(String qnaId){
        return qnaIdEq(qnaId).and(parentIdIsNotNull());
    }
    private BooleanExpression qnaIdEq(String qnaId) {
        return replyEntity.qnaId.eq(qnaId);
    }
    private  BooleanExpression parentIdIsNull(){
        return replyEntity.parent.id.isNull();
    }
    private  BooleanExpression parentIdIsNotNull(){
        return replyEntity.parent.id.isNotNull();
    }
}
