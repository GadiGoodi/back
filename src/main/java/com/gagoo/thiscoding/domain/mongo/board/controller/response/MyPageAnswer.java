package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.AnswerList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswerList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageAnswer {

    private String answerId;
    private String content;
    private String nickname;
    private String parentId;
    private String profileImage;
    private Long likeCount;
    private Long replyCount;
    private LocalDateTime createDate;
    private boolean isSelected;

    public static MyPageAnswer from(MyPageAnswerList myPageAnswerList){
        return MyPageAnswer.builder()
                .answerId(myPageAnswerList.answerId())
                .content(myPageAnswerList.content())
                .nickname(myPageAnswerList.nickname())
                .profileImage(myPageAnswerList.profileImage())
                .parentId(myPageAnswerList.parentId())
                .likeCount(myPageAnswerList.likeCount())
                .replyCount(myPageAnswerList.replyCount())
                .createDate(myPageAnswerList.createDate())
                .isSelected(myPageAnswerList.isSelected())
                .build();
    }
}
