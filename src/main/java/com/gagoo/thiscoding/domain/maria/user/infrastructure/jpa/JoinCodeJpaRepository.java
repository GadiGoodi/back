package com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface JoinCodeJpaRepository extends CrudRepository<JoinCodeEntity, String> {
}
