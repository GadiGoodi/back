package com.gagoo.thiscoding.domain.maria.manager.controller.port;

import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManagerService {

    Page<Manager> getAllManagerNotices(Pageable pageable);

    Manager createAdminNotices(ManagerNoticesCreate request);

    void deleteManagerNotices(Long id);

    Manager updateAdminNotices(Long id, ManagerNoticesUpdate request);

    Manager getNotices(Long id);
}

