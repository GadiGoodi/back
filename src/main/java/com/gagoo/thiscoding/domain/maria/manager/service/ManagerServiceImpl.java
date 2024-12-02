package com.gagoo.thiscoding.domain.maria.manager.service;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesPost;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesUpdate;
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
        return (managerRepository.findAll(pageable).map(ManagerEntity::toModel));
    }

    @Override
    public void createAdminNotices(ManagerNoticesPost request) {
        ManagerEntity results = request.toEntity();
        managerRepository.save(results);
    }

    @Override
    public void deleteManagerNotices(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    public void updateAdminNotices(Long id, ManagerNoticesUpdate request) {
        Manager findNotices = getNotices(id);
        Manager updateNotices = findNotices.updateManagerNotices(request);
        managerRepository.save(updateNotices.toEntity());
    }

    @Override
    public Manager getNotices(Long id) {
        return managerRepository.findById(id).orElseThrow().toModel();
    }
}
