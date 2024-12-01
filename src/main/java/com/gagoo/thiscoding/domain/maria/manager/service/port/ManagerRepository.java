package com.gagoo.thiscoding.domain.maria.manager.service.port;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManagerRepository {

    Page<ManagerEntity> findAll(Pageable pageable);
    ManagerEntity save(ManagerEntity manager);
    void deleteById(Long id);
    Optional<ManagerEntity> findById(Long id);
}

