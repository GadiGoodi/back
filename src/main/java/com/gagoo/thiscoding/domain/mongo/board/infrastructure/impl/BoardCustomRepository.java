package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.KeywordSearchResult;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswer;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookmarkQuestion;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepository {

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

    /**
     * 채택 답변 우선 정렬하여 조회
     */
    public Page<BoardDocument> getAnswerByQnaIdSortByIsSelected(String qnaId, Pageable pageable) {
        Aggregation aggregation = getSelectedAnswerPriorityAggregation(qnaId, pageable);

        AggregationResults<BoardDocument> results = mongoTemplate.aggregate(aggregation, "qna", BoardDocument.class);

        List<BoardDocument> answerList = results.getMappedResults();

        Query countQuery = new Query(Criteria.where("parentId").is(qnaId));

        return PageableExecutionUtils.getPage(
                answerList,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class));
    }

    /**
     * 채택된 답변 우선 정렬 및 내림차순 조회 쿼리
     */
    private Aggregation getSelectedAnswerPriorityAggregation(String qnaId, Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
            match(Criteria.where("parentId").is(qnaId)),
                sort(Sort.by(Sort.Order.desc("isSelected"),
                        Sort.Order.desc("createDate"))),
                skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                limit(pageable.getPageSize())
        );

        return aggregation;
    }

    /**
     * 키워드 검색
     */
    public Page<KeywordSearchResult> findByKeyword(String keyword, Pageable pageable) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("title").regex(keyword, "i"),
                Criteria.where("content").regex(keyword, "i")
        );

        Aggregation aggregation = newAggregation(
                match(criteria),

                lookup("qna_stats", "_id", "qnaId", "stats"),

                unwind("stats"),

                sort(pageable.getSort().isEmpty() ? Sort.by(Sort.Order.desc("createDate")) : pageable.getSort()),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize()),

                project()
                        .and("_id").as("qnaId")
                        .and("language").as("language")
                        .and("title").as("title")
                        .and("content").as("content")
                        .and("nickname").as("nickname")
                        .and("stats.viewCount").as("viewCount")
                        .and("stats.answerCount").as("answerCount")
                        .and("isAdopted").as("isAdopted")
                        .and("createDate").as("createDate")
        );

        AggregationResults<KeywordSearchResult> results =
                mongoTemplate.aggregate(aggregation, "qna", KeywordSearchResult.class);

        List<KeywordSearchResult> content = results.getMappedResults();

        Query countQuery = new Query(criteria);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class)
        );
    }

    /**
     * 내가 작성한 질문 조회
     */
    public Page<MyPageQuestion> findQuestionsByUserId(Long userId, Pageable pageable) {
        Criteria criteria = Criteria.where("userId").is(userId)
                .and("parentId").exists(false);

        Aggregation aggregation = newAggregation(
                match(criteria),

                lookup("qna_stats", "_id", "qnaId", "stats"),

                unwind("stats"),

                sort(pageable.getSort().isEmpty()
                        ? Sort.by(Sort.Order.desc("createDate"))
                        : pageable.getSort()),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize()),

                project()
                        .and("_id").as("qnaId")
                        .and("language").as("language")
                        .and("title").as("title")
                        .and("content").as("content")
                        .and("stats.viewCount").as("viewCount")
                        .and("stats.answerCount").as("answerCount")
                        .and("isAdopted").as("isAdopted")
                        .and("createDate").as("createDate")
        );

        AggregationResults<MyPageQuestion> results =
                mongoTemplate.aggregate(aggregation, "qna", MyPageQuestion.class);

        List<MyPageQuestion> content = results.getMappedResults();

        Query countQuery = new Query(criteria);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class)
        );
    }

    /**
     * 내가 작성한 답변 조회
     */
    public Page<MyPageAnswer> findAnswerByUserId(Long userId, Pageable pageable) {
        Criteria criteria = Criteria.where("userId").is(userId)
                .and("parentId").exists(true);

        Aggregation aggregation = newAggregation(
                match(criteria),

                lookup("qna_stats", "_id", "qnaId", "stats"),

                unwind("stats"),

                sort(pageable.getSort().isEmpty()
                        ? Sort.by(Sort.Order.desc("createDate"))
                        : pageable.getSort()),                skip(pageable.getOffset()),
                limit(pageable.getPageSize()),

                project()
                        .and("_id").as("answerId")
                        .and("parentId").as("parentId")
                        .and("language").as("qnaLanguage")
                        .and("title").as("qnaTitle")
                        .and("content").as("content")
                        .and("stats.replyCount").as("replyCount")
                        .and("isAdopted").as("isAdopted")
                        .and("createDate").as("createDate")
        );

        AggregationResults<MyPageAnswer> results =
                mongoTemplate.aggregate(aggregation, "qna", MyPageAnswer.class);

        List<MyPageAnswer> content = results.getMappedResults();

        Query countQuery = new Query(criteria);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class)
        );
    }

    /**
     * 내가 북마크한 질문 조회
     */
    public Page<MyPageBookmarkQuestion> findBookmarkedQuestionByIdIn(List<String> bookmarkedQuestionList, Pageable pageable) {
        if (bookmarkedQuestionList == null || bookmarkedQuestionList.isEmpty()) {
            return Page.empty(pageable);
        }

        Criteria criteria = Criteria.where("_id").in(bookmarkedQuestionList);

        Aggregation aggregation = newAggregation(
                match(criteria),

                lookup("qna_stats", "_id", "qnaId", "stats"),

                unwind("stats", true),

                sort(pageable.getSort().isEmpty()
                        ? Sort.by(Sort.Order.desc("createDate"))
                        : pageable.getSort()),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize()),

                project()
                        .and("_id").as("qnaId")
                        .and("language").as("language")
                        .and("title").as("title")
                        .and("content").as("content")
                        .and("stats.viewCount").as("viewCount")
                        .and("stats.answerCount").as("answerCount")
                        .and("isAdopted").as("isAdopted")
                        .and("createDate").as("createDate")
        );

        AggregationResults<MyPageBookmarkQuestion> results =
                mongoTemplate.aggregate(aggregation, "qna", MyPageBookmarkQuestion.class);

        List<MyPageBookmarkQuestion> content = results.getMappedResults();

        Query countQuery = new Query(criteria);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class)
        );    }


    /**
     * QnA에 달린 답변이 채택되면
     * QnA의 isAdopt 값 true로 변경
     */
    public void markParentAsAdopted(String qnaId) {
        Query query = new Query(Criteria.where("_id").is(qnaId));
        Update update = new Update().set("isAdopt", true);

        mongoTemplate.updateFirst(query, update, BoardDocument.class);
    }

    /**
     * 답변 채택
     */
    public void adoptAnswer(String qnaId) {
        Query query = new Query(Criteria.where("_id").is(qnaId));
        Update update = new Update().set("isSelected", true);

        mongoTemplate.updateFirst(query, update, BoardDocument.class);
    }

    /**
     * 질문 전체조회
     */
    public Page<BoardDocument> findRootQuestions(Pageable pageable) {
        Criteria criteria = Criteria.where("parentId").exists(false)
                .and("isBlind").is(false); // 블라인드 제외

        Aggregation aggregation = newAggregation(
                match(criteria),
                sort(pageable.getSort().isEmpty() ? Sort.by(Sort.Order.desc("createDate")) : pageable.getSort()),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize())
        );

        AggregationResults<BoardDocument> results =
                mongoTemplate.aggregate(aggregation, "qna", BoardDocument.class);

        List<BoardDocument> content = results.getMappedResults();

        Query countQuery = new Query(criteria);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(countQuery, BoardDocument.class)
        );
    }
}
