package com.gagoo.thiscoding.domain.maria.manager.service;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.manager.service.exception.NoticesNotFoundException;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Builder
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    //공지사항 목록 전체 조회
    @Override
    public Page<Manager> getAllManagerNotices(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    //공지사항 작성
    @Override
    public Manager createAdminNotices(ManagerNoticesCreate request) {
        Manager result = Manager.create(request);
       return managerRepository.save(result);
    }

    //공지사항 삭제
    @Override
    public void deleteManagerNotices(Long id) {
        validateNoticeId(id);

        managerRepository.deleteById(id);
    }

    //공지사항 수정
    @Override
    public Manager updateAdminNotices(Long id, ManagerNoticesUpdate request) {
        Manager findNotices = getById(id);
        Manager updateNotices = findNotices.updateManagerNotices(request);
       return managerRepository.save(updateNotices);
    }

    //공지사항 상세 조회
    @Override
    public Manager getNotices(Long id) {
        return getById(id);
    }

    /**
     *  공지사항이 존재하는지 확인
     */
    private void validateNoticeId(Long id){
        if (!managerRepository.existsById(id)) {
            throw new NoticesNotFoundException(ErrorCode.NOTICES_NOT_FOUND);
        }
    }

    /**
     * id에 해당하는 공지사항 가져오기
     * 예외처리
     */
    public Manager getById(Long id){
        return managerRepository.findById(id).orElseThrow(() -> new NoticesNotFoundException(ErrorCode.NOTICES_NOT_FOUND));
    }
}
