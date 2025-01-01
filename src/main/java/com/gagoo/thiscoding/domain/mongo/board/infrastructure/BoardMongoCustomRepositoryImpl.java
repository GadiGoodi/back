package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardMongoCustomRepositoryImpl implements BoardCustomRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 키워드로 검색
     */

    public Page<BoardDocument> searchByKeyword(String keyword, Pageable pageable) {
        Query query = new Query();

        query.addCriteria(new Criteria().orOperator(
            Criteria.where("title").regex(keyword, "i"),
            Criteria.where("content").regex(keyword, "i")
        ));

        long total = mongoTemplate.count(query, "qna");

//        query.with(pageable);

        List<BoardDocument> documents = mongoTemplate.find(query, BoardDocument.class, "qna");

        return new PageImpl<>(documents, pageable, total);
    }
}
