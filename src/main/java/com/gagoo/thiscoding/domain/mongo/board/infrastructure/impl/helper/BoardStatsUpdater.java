package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl.helper;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardStatsUpdater {

    private final MongoTemplate mongoTemplate;

    public void incrementViewCount(String boardId) {
        updateField(boardId, "viewCount", 1);
    }

    public void incrementAnswerCount(String boardId) {
        updateField(boardId, "answerCount", 1);
    }

    public void incrementReplyCount(String boardId) {
        updateField(boardId, "replyCount", 1);
    }

    public void incrementLikeCount(String boardId) {
        updateField(boardId, "likeCount", 1);
    }

    public void decrementLikeCount(String boardId) {
        updateField(boardId, "likeCount", -1);
    }

    public void decrementReplyCount(String boardId) {
        updateField(boardId, "replyCount", -1);
    }

    private void updateField(String boardId, String field, int amount) {
        Query query = new Query(Criteria.where("boardId").is(boardId));
        Update update = new Update().inc(field, amount);
        mongoTemplate.updateFirst(query, update, BoardStats.class);
    }

}
