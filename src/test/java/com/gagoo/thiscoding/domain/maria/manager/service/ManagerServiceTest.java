package com.gagoo.thiscoding.domain.maria.manager.service;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.manager.mock.FakeManagerRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ManagerServiceTest {

    private ManagerServiceImpl managerService;

    @BeforeEach
    void init() {
        FakeManagerRepository fakeManagerRepository = new FakeManagerRepository();

        this.managerService = ManagerServiceImpl.builder()
                .managerRepository(fakeManagerRepository)
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

        fakeManagerRepository.save(Manager.builder()
                .id(1L)
                .content("testContent1")
                .manager(user)
                .title("testTitle1")
                .category("FAQ1")
                .build());
        fakeManagerRepository.save(Manager.builder()
                .id(2L)
                .content("testContent2")
                .manager(user)
                .title("testTitle2")
                .category("FAQ2")
                .build());
    }

    @Test
    public void getAllManagerNotice로_공지사항_조회() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Manager> result = managerService.getAllManagerNotices(pageable);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        //성공
        assertThat(result.getContent().get(0).getContent()).isEqualTo("testContent1");
        assertThat(result.getContent().get(1).getContent()).isEqualTo("testContent2");

        //실패 - 0번째 인덱스 데이터에서 1번째 데이터의 Content를 검증
//        assertThat(result.getContent().get(0).getContent()).isEqualTo("testContent2");
    }

    @Test
    public void getNoties로_아이디에_해당하는_공지사항_조회() {
        Manager result = managerService.getNotices(1L);

        //성공
        assertThat(result.getTitle()).isEqualTo("testTitle1");
        assertThat(result.getCategory()).isEqualTo("FAQ1");

        //실패
//        assertThat(result.getTitle()).isEqualTo("testTitle2");
//        assertThat(result.getContent()).isEqualTo("testContent2");

    }

    @Test
    public void createAdminNotices로_공지사항_작성() {

        User user = User.builder()
                .id(1L)
                .email("test02@test.com")
                .nickname("canelo")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();
        ManagerNoticesCreate create = ManagerNoticesCreate.builder()
                .id(3L)
                .content("testContent3")
                .manager(user)
                .title("testTitle3")
                .category("FAQ3")
                .build();

        Manager manager = managerService.createAdminNotices(create);

        //성공
        assertThat(manager.getTitle()).isEqualTo("testTitle3");
        assertThat(manager.getCategory()).isEqualTo("FAQ3");
        assertThat(manager.getContent()).isEqualTo("testContent3");

        //실패
//        assertThat(result.getTitle()).isEqualTo("testTitle1");
//        assertThat(result.getCategory()).isEqualTo("FAQ1");
//        assertThat(result.getContent()).isEqualTo("testContent1");
    }

    @Test
    public void deleteManagerNotice로_공지사항_삭제() {

        User user = User.builder()
                .id(1L)
                .email("test02@test.com")
                .nickname("canelo")
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();
        ManagerNoticesCreate manager = ManagerNoticesCreate.builder()
                .id(4L)
                .content("testContent4")
                .manager(user)
                .title("testTitle4")
                .category("FAQ4")
                .build();

        Manager result = managerService.createAdminNotices(manager);

        //삭제 전 데이터가 존재하는지 확인
        assertThat(result.getId()).isNotNull();

        //데이터 삭제
        managerService.deleteManagerNotices(4L);

        // managerService.getNotices(4L); 메서드 실행시 IllegalArgumentException로 예외처리됨.
        assertThrows(IllegalArgumentException.class, () -> {
            managerService.getNotices(4L);
        });
    }

    @Test
    public void getNotices로_공지사항_업데이트() {

        ManagerNoticesUpdate manager = ManagerNoticesUpdate.builder()
                .content("updateContent")
                .title("updateTitle")
                .build();

        managerService.updateAdminNotices(2L, manager);

        Manager result = managerService.getNotices(2L);

        //성공
        assertThat(result.getTitle()).isEqualTo("updateTitle");
        assertThat(result.getContent()).isEqualTo("updateContent");

        //실패
//        assertThat(result.getTitle()).isEqualTo("testTitle2");
//        assertThat(result.getContent()).isEqualTo("testContent2");

    }

}