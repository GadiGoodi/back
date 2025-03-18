package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = {"test"})
@Sql(scripts = "/sql/coderoom-test-data.sql")
class CodeRoomRepositoryImplTest {
    @Autowired
    private CodeRoomCustomRepository codeRoomCustomRepository;

    @Nested
    @DisplayName("(참여 중인) 코드방으로 참여자 목록 조회")
    class UserListGetUnitTest {
        @Test
        @DisplayName("(참여 중인) 코드방으로 참여자 목록을 조회할 수 있다.")
        void findUserListByUserCodeRoom_참여_중인_코드방으로_참여자_목록_조회_성공() {
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

            CodeRoom codeRoom = CodeRoom.builder()
                    .id(1L)
                    .uuid("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                    .title("teach me")
                    .content("how to print the hello world?")
                    .language("java")
                    .headCount(5)
                    .build();

            UserCodeRoom userCodeRoom = UserCodeRoom.builder()
                    .id(1L)
                    .user(user01)
                    .codeRoom(codeRoom)
                    .isActivated(true)
                    .build();

            // when
            List<User> userList = codeRoomCustomRepository.findUserListByUserCodeRoom(userCodeRoom);

            // then
            assertThat(userList).isNotEmpty();
            assertThat(userList.size()).isEqualTo(5);
            assertThat(userList).anyMatch(user -> user.getId().equals(user01.getId()));

        }
    }

    @Nested
    @DisplayName("사용자로 (참여 중인) 코드방 목록 조회")
    class UserCodeRoomPageGetUnitTest {
        @Test
        @DisplayName("사용자로 (참여 중인) 코드방 목록을 조회할 수 있다.")
        void findAllUserCodeRoomByUser_사용자로_참여_중인_코드방_목록_조회_성공() {
            // given
            Pageable pageable = PageRequest.of(0, 10);
            User user = User.builder()
                    .id(1L)
                    .email("test01@test.com")
                    .nickname("test01")
                    .isActivated(true)
                    .isBanned(false)
                    .role(Role.USER)
                    .social(Social.THIS_CODING)
                    .build();

            // when
            Page<UserCodeRoom> result = codeRoomCustomRepository.findAllUserCodeRoomByUser(user, pageable);

            // then
            assertThat(result.getTotalElements()).isEqualTo(1);
        }
    }
}
