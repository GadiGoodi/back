package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipatingCodeRoomResponseTest {

    @Test
    @DisplayName("ParticipatingCodeRoomResponse를 생성 및 반환할 수 있다.")
    void ParticipatingCodeRoomResponse로_Response를_반환() {
        // given
        User user01 = User.builder()
                .id(1L)
                .email("test01@test.com")
                .nickname("test01")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();

        User user02 = User.builder()
                .id(2L)
                .email("test02@test.com")
                .nickname("test02")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();

        CodeRoom codeRoom = CodeRoom.builder()
                .id(1L)
                .uuid("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .title("teach me")
                .content("how to print the hello world?")
                .language("java")
                .headCount(2)
                .build();

        UserCodeRoom userCodeRoom = UserCodeRoom.builder()
                .id(1L)
                .user(user01)
                .codeRoom(codeRoom)
                .isActivated(true)
                .build();

        List<User> userList = Arrays.asList(user01, user02);

        // when
        ParticipatingCodeRoomResponse participatingCodeRoomResponse = ParticipatingCodeRoomResponse.from(userCodeRoom, userList);

        // then
        assertThat(participatingCodeRoomResponse.getUserCodeRoomId()).isEqualTo(1L);
        assertThat(participatingCodeRoomResponse.getCodeRoomId()).isEqualTo(1L);
        assertThat(participatingCodeRoomResponse.getTitle()).isEqualTo("teach me");
        assertThat(participatingCodeRoomResponse.getContent()).isEqualTo("how to print the hello world?");
        assertThat(participatingCodeRoomResponse.getLanguage()).isEqualTo("java");
        assertThat(participatingCodeRoomResponse.getUserList()).isEqualTo(Arrays.asList(user01, user02));
        assertThat(participatingCodeRoomResponse.getHeadCount()).isEqualTo(2);
    }
}
