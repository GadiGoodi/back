package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswerList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookMarkList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQnAList;
import com.gagoo.thiscoding.global.security.infrastructure.FakeSecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BoardServiceImplTest {

    public BoardService boardService;
    public User testUser;
    public Board testBoard;
    public Board testAnswer;
    public

    @BeforeEach
    void init() {
        User user = User.builder()
                .id(1L)
                .email("junsj1230@naver.com")
                .password("encoded-password")
                .nickname("TestUser")
                .imageUrl("test-image-url")
                .isActivated(true)
                .isBanned(false)
                .build();


        TestContainer testContainer = TestContainer.builder()
                .securityUtils(new FakeSecurityUtils(user.getEmail()))
                .build();

        this.boardService = testContainer.boardService;
        this.testUser = testContainer.userRepository.save(user);

        Board board = Board.create(testUser, BoardCreate.of("testTitle1", "testContent1", "Java"));

        this.testBoard = testContainer.boardRepository.save(board);

        Board answer = Board.writeAnswer(testUser,testBoard.getId(),"testAnswer");

        this.testAnswer = testContainer.boardRepository.save(answer);

        Bookmark bookmark = Bookmark.create(testUser,testBoard.getId());

        testContainer.bookmarkRepository.save(bookmark);

    }

    @Test
    @DisplayName("마이페이지에서 내가 작성한 QnA 질문을 페이징하여 조회할 수 있다")
    void getMyPagePostQnA_페이징_조회_성공() {

        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<MyPageQnAList> result = boardService.getMyPagePostQnA(pageable);

        //then
        assertThat(result.getContent().get(0).title()).isEqualTo("testTitle1");
        assertThat(result.getContent().get(0).content()).isEqualTo("testContent1");
    }

    @Test
    @DisplayName("마이페이지에서 내가 작성한 QnA 답변을 페이징하여 조회할 수 있다")
    void getMyPagePostAnswer_페이징_조회_성공() {

        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<MyPageAnswerList> result = boardService.getMyPagePostAnswer(pageable);

        //then
        assertThat(result.getContent().get(0).content()).isEqualTo("testAnswer");
    }

    @Test
    @DisplayName("마이페이지에서 내가 북마크한 QnA 질문을 페이징하여 조회할 수 있다")
    void getMyPageBookMarkQuestion_페이징_조회_성공 () {

        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<MyPageBookMarkList> result = boardService.getMyPageBookMarkQuestion(pageable);

        //then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

}