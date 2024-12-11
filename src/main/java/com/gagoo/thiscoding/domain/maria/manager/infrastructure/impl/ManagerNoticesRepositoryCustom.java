package com.gagoo.thiscoding.domain.maria.manager.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import com.querydsl.core.Query;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.gagoo.thiscoding.domain.maria.manager.infrastructure.QManagerEntity.*;

@RequiredArgsConstructor
public class ManagerNoticesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Manager> findAll(Pageable pageable) {
        List<Manager> results = queryFactory.select(Projections.constructor(Manager.class,
                        managerEntity.id,
                        managerEntity.manager,
                        managerEntity.title,
                        managerEntity.category,
                        managerEntity.viewCount
                ))
                .from(managerEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(managerEntity.id.desc())
                .fetch();


        JPAQuery<Long> countQuery = queryFactory.select(managerEntity.count()).from(managerEntity);

        return PageableExecutionUtils.getPage(results, pageable,countQuery::fetchOne);
    }

//    QueryDsl 작성부분

}
