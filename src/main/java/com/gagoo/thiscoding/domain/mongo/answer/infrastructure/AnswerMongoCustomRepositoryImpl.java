package com.gagoo.thiscoding.domain.mongo.answer.infrastructure;

import com.gagoo.thiscoding.domain.mongo.answer.service.port.AnswerCustomRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class AnswerMongoCustomRepositoryImpl implements AnswerCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Long> getTop10Users() {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("isSelected").is(true)),
                group("userId").count().as("selectedCount"),
                sort(Sort.Direction.DESC, "selectedCount"),
                limit(10)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "answer",
                Document.class
        );

        return results.getMappedResults()
                .stream()
                .map(doc -> doc.get("_id", Long.class))
                .toList();
    }
}
