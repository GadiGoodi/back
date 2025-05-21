package com.gagoo.thiscoding.domain.mongo.board.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageAnswer;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageBookmark;
import com.gagoo.thiscoding.domain.mongo.board.controller.response.MyPageQnA;
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

    private final BoardService boardService;

    //마이페이지 작성한 QnA 질문 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/qna")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageQnA>> getMyPageQnA(Pageable pageable) {

        Page<MyPageQnA> qnaResult = boardService.getMyPagePostQnA(pageable).map(MyPageQnA::from);
        return ApiResponse.ok(CustomPageDto.of(qnaResult),"마이페이지 작성한 QnA 질문 조회 완료");

    }

    //마이페이지 작성한 QnA 답변 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/answer")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageAnswer>> getMyPageAnswer(Pageable pageable) {
        Page<MyPageAnswer> answerResult = boardService.getMyPagePostAnswer(pageable).map(MyPageAnswer::from);
        return ApiResponse.ok(CustomPageDto.of(answerResult),"마이페이지 작성한 QnA 답변 완료");

    }

    //마이페이지 북마크한 QnA 조회
    @AuthorizationRequired(value = {Role.USER,Role.ADMIN})
    @GetMapping("/bookmark")
    @ConvertToOneBase
    public ApiResponse<CustomPageDto<MyPageBookmark>> getMyPageBookMarkQnA(Pageable pageable) {
        Page<MyPageBookmark> bookmarkList = boardService.getMyPageBookMarkQuestion(pageable).map(MyPageBookmark::from);

        return ApiResponse.ok(CustomPageDto.of(bookmarkList),"마이페이지 북마크한 QnA 질문 조회 완료");
    }
}
