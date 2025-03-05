package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeRoomEnterResponseTest {

    @Test
    @DisplayName("CodeRoomEnterResponse를 생성 및 반환할 수 있다.")
    void CodeRoomEnterResponse로_Response를_반환() {
        // given
        CodeRoomEnter codeRoomEnter = CodeRoomEnter.builder()
                .roomId(1L)
                .language("java")
                .value("/* Java */\\n\\nclass Main {\\n\\tstatic public void main(String []args) {\\n\\t\\tSystem.out.println(\"Hello, World!\");\\n\\t}\\n}")
                .codeId("676574bae5e6964725265993")
                .build();

        // when
        CodeRoomEnterResponse codeRoomEnterResponse = CodeRoomEnterResponse.from(codeRoomEnter);

        // then
        assertThat(codeRoomEnterResponse.getRoomId()).isEqualTo(1);
        assertThat(codeRoomEnterResponse.getLanguage()).isEqualTo("java");
        assertThat(codeRoomEnterResponse.getValue()).isEqualTo("/* Java */\\n\\nclass Main {\\n\\tstatic public void main(String []args) {\\n\\t\\tSystem.out.println(\"Hello, World!\");\\n\\t}\\n}");
        assertThat(codeRoomEnterResponse.getCodeId()).isEqualTo("676574bae5e6964725265993");
    }
}
