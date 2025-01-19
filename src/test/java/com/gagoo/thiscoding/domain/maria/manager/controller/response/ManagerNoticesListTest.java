package com.gagoo.thiscoding.domain.maria.manager.controller.response;

import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesDetail;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesList;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ManagerNoticesListTest {

    @Test
    public void ManagerNoticesList로_Response반환() {

        //given
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
                .category("TestCategory")
                .build();

        //when
        ManagerNoticesList list = ManagerNoticesList.from(manager);

        //then
        assertThat(list.getTitle()).isEqualTo("testTitle");
        assertThat(list.getCategory()).isEqualTo("TestCategory");

    }

}