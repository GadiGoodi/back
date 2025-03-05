package com.gagoo.thiscoding.domain.maria.coderoom.domain;


import static org.assertj.core.api.Assertions.assertThat;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.domain.mock.FakeUuidHolder;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import org.junit.jupiter.api.Test;

public class CodeRoomTest {

    @Test
    void create로_코드방_생성() {
        // given
        CodeRoomCreate codeRoomCreate = CodeRoomCreate.builder()
                .title("teach me")
                .content("how to print the hello world?")
                .language("java")
                .build();

        TestContainer testContainer = TestContainer.builder().build();

        // when
        CodeRoom codeRoom = CodeRoom.create(codeRoomCreate, testContainer.uuidHolder);

        // then
        assertThat(codeRoom.getId()).isNull();
        assertThat(codeRoom.getTitle()).isEqualTo("teach me");
        assertThat(codeRoom.getContent()).isEqualTo("how to print the hello world?");
        assertThat(codeRoom.getLanguage()).isEqualTo("java");
        assertThat(codeRoom.getHeadCount()).isEqualTo(1);
    }

    @Test
    void join_은_codeRoom_의_headCount_를_증가시킨다(){
        // given
        CodeRoom codeRoom = CodeRoom.builder()
            .headCount(1)
            .build();

        // when
        codeRoom.join();

        // then
        assertThat(codeRoom.getHeadCount()).isEqualTo(2);
    }

    @Test
    void exit는_codeRoom의_heatCount를_감소시킨다() {
        // given
        CodeRoom codeRoom = CodeRoom.builder()
                .headCount(2)
                .build();

        // when
        codeRoom.exit();

        // then
        assertThat(codeRoom.getHeadCount()).isEqualTo(1);
    }
}
