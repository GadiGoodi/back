package com.gagoo.thiscoding.domain.maria.manager.controller.port;

import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesPost;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {

    Page<Manager> getAllManagerNotices(Pageable pageable);
    void createAdminNotices(ManagerNoticesPost request);
    void deleteManagerNotices(Long id);
    void updateAdminNotices(Long id ,ManagerNoticesUpdate request);
    Manager getNotices(Long id);
}
