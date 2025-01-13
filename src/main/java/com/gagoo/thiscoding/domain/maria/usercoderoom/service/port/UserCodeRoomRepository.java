package com.gagoo.thiscoding.domain.maria.usercoderoom.service.port;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.util.Optional;

public interface UserCodeRoomRepository {
    Optional<UserCodeRoom> findById(Long codeRoomId);

    UserCodeRoom save(UserCodeRoom userCodeRoom);

    void delete(UserCodeRoom userCodeRoom);
}
