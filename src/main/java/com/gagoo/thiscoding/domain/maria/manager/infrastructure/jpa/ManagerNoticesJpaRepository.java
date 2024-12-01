package com.gagoo.thiscoding.domain.maria.manager.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerNoticesJpaRepository extends JpaRepository<ManagerEntity,Long>{

}
