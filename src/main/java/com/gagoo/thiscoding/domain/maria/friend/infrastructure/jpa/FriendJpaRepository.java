package com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.friend.infrastructure.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendJpaRepository extends JpaRepository<FriendEntity, Long> {

}
