package com.gagoo.thiscoding.domain.maria.manager.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.manager.domain.Manager;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import com.gagoo.thiscoding.domain.maria.manager.infrastructure.jpa.ManagerNoticesJpaRepository;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepository {

    private final ManagerNoticesJpaRepository managerNoticesJpaRepository;

    @Override
    public Page<ManagerEntity> findAll(Pageable pageable) {
        return managerNoticesJpaRepository.findAll(pageable);
    }

    @Override
    public ManagerEntity save(ManagerEntity request) {
        return managerNoticesJpaRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        managerNoticesJpaRepository.deleteById(id);
    }

    @Override
    public Optional<ManagerEntity> findById(Long id) {
        return managerNoticesJpaRepository.findById(id);
    }

}