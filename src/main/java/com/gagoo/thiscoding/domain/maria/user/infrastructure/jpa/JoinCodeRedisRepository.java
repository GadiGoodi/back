package com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeRedis;
import org.springframework.data.repository.CrudRepository;

public interface JoinCodeRedisRepository extends CrudRepository<JoinCodeRedis, String> {
}
