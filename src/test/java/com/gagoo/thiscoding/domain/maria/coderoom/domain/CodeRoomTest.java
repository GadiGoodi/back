package com.gagoo.thiscoding.domain.maria.coderoom.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CodeRoomTest {

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
}
