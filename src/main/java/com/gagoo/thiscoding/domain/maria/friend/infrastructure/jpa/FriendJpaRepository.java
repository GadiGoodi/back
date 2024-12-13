package com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.friend.infrastructure.FriendEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendJpaRepository extends JpaRepository<FriendEntity, Long> {

    @Query("SELECT f FROM FriendEntity f WHERE f.sender.id = :userId OR f.receiver.id = :userId")
    List<FriendEntity> findAllByUserId(@Param("userId") Long userId);
    Long deleteByReceiverIdAndSenderId(Long receiverId, Long senderId);
    boolean existsByReceiverIdAndSenderId(Long receiverId, Long senderId);
}
