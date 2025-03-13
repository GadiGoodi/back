package com.gagoo.thiscoding.domain.maria.usercoderoom.service.port;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.util.Optional;

public interface UserCodeRoomRepository {
    UserCodeRoom getById(Long codeRoomId);

    Optional<UserCodeRoom> findById(Long codeRoomId);

    UserCodeRoom save(UserCodeRoom userCodeRoom);

    void deleteById(Long userCodeRoomId);

    boolean existByCodeRoomIdAndUserId(Long codeRoomId, Long userId);

    Optional<UserCodeRoom> findByCodeRoomIdAndUserId(Long codeRoomId, Long userId);
}
