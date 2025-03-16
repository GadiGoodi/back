package com.gagoo.thiscoding.domain.maria.manager.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesList;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = {"test"})
@Sql(scripts = "/sql/manager-board-test-data.sql")
class ManagerRepositoryImplTest {

    @Autowired
    private ManagerNoticesRepositoryCustom managerCustomRepository;

    @Test
    public void findAll로_공지사항_목록_조회_성공() {

        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Manager> result = managerCustomRepository.findAll(pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getContent()).isNotNull();

        // 변환: Manager -> ManagerNoticesList
        List<ManagerNoticesList> managerNoticesList = result.getContent().stream()
                .map(ManagerNoticesList::from)
                .toList();

        // 변환 후 리스트 검증
        assertThat(managerNoticesList.get(0).getId()).isEqualTo(result.getContent().get(0).getId());
        assertThat(managerNoticesList.get(0).getTitle()).isNotNull();
        assertThat(managerNoticesList.get(0).getCategory()).isNotNull();
        assertThat(managerNoticesList.get(0).getViewCount()).isNotNull();
        assertThat(managerNoticesList.get(0).getCreateDate()).isNotNull();
    }
}
