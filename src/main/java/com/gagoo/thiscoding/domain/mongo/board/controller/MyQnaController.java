package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardQueryService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageAnswerResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageBookmarkQuestionResponse;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageQuestionResponse;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyQnaController {

    private final BoardQueryService boardQueryService;

    //마이페이지 작성한 QnA 질문 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/qna")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageQuestionResponse>> getMyPageQnA(Pageable pageable) {

        Page<MyPageQuestionResponse> qnaResult = boardQueryService.getMyPagePostQnA(pageable).map(MyPageQuestionResponse::from);
        return ApiResponse.ok(CustomPageDto.of(qnaResult),"마이페이지 작성한 QnA 질문 조회 완료");

    }

    //마이페이지 작성한 QnA 답변 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/answer")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageAnswerResponse>> getMyPageAnswer(Pageable pageable) {
        Page<MyPageAnswerResponse> answerResult = boardQueryService.getMyPagePostAnswer(pageable).map(MyPageAnswerResponse::from);
        return ApiResponse.ok(CustomPageDto.of(answerResult),"마이페이지 작성한 QnA 답변 완료");

    }

    //마이페이지 북마크한 QnA 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/bookmark")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageBookmarkQuestionResponse>> getMyPageBookMarkQnA(Pageable pageable) {
        Page<MyPageBookmarkQuestionResponse> bookmarkList = boardQueryService.getMyPageBookMarkQuestion(pageable).map(MyPageBookmarkQuestionResponse::from);

        return ApiResponse.ok(CustomPageDto.of(bookmarkList),"마이페이지 북마크한 QnA 질문 조회 완료");
    }
}
