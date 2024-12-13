package com.gagoo.thiscoding.domain.maria.manager.service;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    //공지사항 목록 전체 조회
    @Override
    public Page<Manager> getAllManagerNotices(Pageable pageable) {
        //entity > domain
        return (managerRepository.findAll(pageable));
    }

    //공지사항 작성
    @Override
    public void createAdminNotices(ManagerNoticesCreate request) {
        Manager result = Manager.create(request);
        managerRepository.save(result);
    }

    //공지사항 삭제
    @Override
    public void deleteManagerNotices(Long id) {
        managerRepository.deleteById(id);
    }

    //공지사항 수정
    @Override
    public void updateAdminNotices(Long id, ManagerNoticesUpdate request) {
        Manager findNotices = getNotices(id);
        Manager updateNotices = findNotices.updateManagerNotices(request);
        managerRepository.save(updateNotices);
    }

    //공지사항 상세 조회
    @Override
    public Manager getNotices(Long id) {
        return managerRepository.findById(id);
    }
}
