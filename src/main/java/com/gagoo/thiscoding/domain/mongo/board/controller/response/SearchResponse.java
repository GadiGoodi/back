package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchResponse {
    private String id;
    private Long userId;
    private String title;
    private String content;
    private String language;
    private String parentId;
    private Long likeCount;
    private Long viewCount;
    private Long answerCount;
    private boolean isSelected;
    private LocalDateTime createDate;

    @Builder
    public SearchResponse(String id, Long userId, String title, String content, String language,
        String parentId, Long likeCount, Long viewCount, Long answerCount, boolean isSelected, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.language = language;
        this.parentId = parentId;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.isSelected = isSelected;
        this.createDate = createDate;
    }

    public static SearchResponse from(Search search) {
        return SearchResponse.builder()
            .id(search.getId())
            .userId(search.getUserId())
            .title(search.getTitle())
            .content(search.getContent())
            .language(search.getLanguage())
            .parentId(search.getParentId())
            .likeCount(search.getLikeCount())
            .viewCount(search.getViewCount())
            .answerCount(search.getAnswerCount())
            .isSelected(search.isSelected())
            .createDate(search.getCreateDate())
            .build();
    }
}
