package com.gagoo.thiscoding.domain.maria.manager.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ManagerTest {

    @Test
    public void create로_관리자_공지사항_작성() {

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

        ManagerNoticesCreate managerCreate = ManagerNoticesCreate.builder()
                .id(1L)
                .manager(user)
                .title("테스트제목")
                .content("testContent")
                .category("FAQ")
                .build();

        // when
        Manager manager = Manager.create(managerCreate);

        // then
        assertThat(manager.getId()).isEqualTo(1L);
        assertThat(manager.getManager()).isEqualTo(user);
        assertThat(manager.getTitle()).isEqualTo("테스트제목");
        assertThat(manager.getContent()).isEqualTo("testContent");
        assertThat(manager.getCategory()).isEqualTo("FAQ");
    }

    @Test
    public void updateManagerNotice로_관리자_공지사항_업데이트() {

        //given
        ManagerNoticesUpdate managerUpdate = ManagerNoticesUpdate.builder()
                .title("updateTitle")
                .content("updateContent")
                .category("FAQ")
                .build();

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
                .title("테스트제목")
                .content("testContent")
                .category("FAQ")
                .build();

        //when
        Manager updateManager = manager.updateManagerNotices(managerUpdate);

        //then
        assertThat(updateManager.getId()).isEqualTo(1L);
        assertThat(updateManager.getManager()).isEqualTo(user);
        assertThat(updateManager.getTitle()).isEqualTo("updateTitle");
        assertThat(updateManager.getContent()).isEqualTo("updateContent");
        assertThat(updateManager.getCategory()).isEqualTo("FAQ");
    }

}