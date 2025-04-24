package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UserCreateRequest;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    public void UserCreate_객체로_생성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        UserCreateRequest createUser = UserCreateRequest.builder()
                .email("test@naver.com")
                .nickname("Liverpool")
                .password("test123!!")
                .checkPassword("test123!!")
                .build();

        // when
        User user = User.create(createUser, testContainer.passwordEncoderHolder);

        // then
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
        assertThat(user.getNickname()).isEqualTo("Liverpool");
        assertThat(user.getPassword()).isEqualTo("encoded-test123!!");
    }

    @Test
    public void 이미지_url_로_프로필_사진을_변경할_수_있다() {

        // given
        User user = User.builder()
                .id(1L)
                .email("test@naver.com")
                .password("encoded-test123!!")
                .nickname("Liverpool")
                .imageUrl("image-url")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();

        // when
        User updateUser = user.updateProfile("update-image-url");

        // then
        assertThat(updateUser.getId()).isEqualTo(user.getId());
        assertThat(updateUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(updateUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(updateUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(updateUser.getImageUrl()).isEqualTo("update-image-url");
        assertThat(updateUser.isActivated()).isEqualTo(user.isActivated());
        assertThat(updateUser.isBanned()).isEqualTo(user.isBanned());
        assertThat(updateUser.getRole()).isEqualTo(user.getRole());
        assertThat(updateUser.getSocial()).isEqualTo(user.getSocial());
    }

}