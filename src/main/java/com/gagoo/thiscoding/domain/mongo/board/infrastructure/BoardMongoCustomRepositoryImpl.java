package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class BoardMongoCustomRepositoryImpl implements BoardCustomRepository {

    private final MongoTemplate mongoTemplate;
    public List<Long> getTop10Users() {
        Aggregation aggregation = getTop10Aggregation();
        AggregationResults<Document> results = getAggregationResults(aggregation);

        return results.getMappedResults()
                .stream()
                .map(doc -> doc.get("_id", Long.class))
                .collect(Collectors.toList());
    }

    /**
     * 채택 많이 받은 상위 10명 집계쿼리
     */
    private static Aggregation getTop10Aggregation() {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("parentId").ne(null)
                        .and("isSelected").is(true)),
                group("userId"),
                sort(Sort.Direction.DESC, "count"),
                limit(10),
                project().andInclude("_id")
        );
        return aggregation;
    }

    /**
     * mongoTemplate으로 상위 10명 데이터 추출
     */
    private AggregationResults<Document> getAggregationResults(Aggregation aggregation) {
        AggregationResults<Document> results =
                mongoTemplate.aggregate(aggregation, "board", Document.class);
        return results;
    }
}
