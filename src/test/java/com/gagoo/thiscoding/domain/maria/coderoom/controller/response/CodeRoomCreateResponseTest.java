package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeRoomCreateResponseTest {

    @Test
    @DisplayName("CodeRoomCreateResponse를 생성 및 반환할 수 있다.")
    void CodeRoomCreateResponse로_Response를_반환() {
        // given
        CodeRoom codeRoom = CodeRoom.builder()
                .id(1L)
                .uuid("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .title("teach me")
                .content("how to print the hello world?")
                .language("java")
                .headCount(1)
                .build();

        // when
        CodeRoomCreateResponse codeRoomCreateResponse = CodeRoomCreateResponse.from(codeRoom);

        // then
        assertThat(codeRoomCreateResponse.getId()).isEqualTo(1);
        assertThat(codeRoomCreateResponse.getUuid().toString()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

}
