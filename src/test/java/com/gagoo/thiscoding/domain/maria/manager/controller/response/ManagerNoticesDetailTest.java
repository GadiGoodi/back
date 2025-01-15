package com.gagoo.thiscoding.domain.maria.manager.controller.response;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ManagerNoticesDetailTest {

    @Test
    public void ManagerNoticeDetail로_Response반환() {
        User user = User.builder()
                .id(1L)
                .email("test02@test.com")
                .nickname("canelo")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();
        Manager manager = Manager.builder()
                .id(1L)
                .manager(user)
                .title("testTitle")
                .content("testContent")
                .category("TestCategory")
                .build();

        ManagerNoticesDetail detail = ManagerNoticesDetail.from(manager);

        assertThat(detail.getTitle()).isEqualTo("testTitle");
        assertThat(detail.getContent()).isEqualTo("testContent");

        //삭제
//        assertThat(detail.getTitle()).isEqualTo("Title1");

    }

}