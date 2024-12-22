package com.gagoo.thiscoding.domain.maria.usercoderoom.service.port;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCodeRoomRepository {
    Page<UserCodeRoom> findByEmail(String email, Pageable pageable);

    Optional<UserCodeRoom> findById(Long codeRoomId);

    Optional<UserCodeRoom> findByCodeRoomIdAndUserEmail(Long codeRoomId, String email);

    UserCodeRoom save(UserCodeRoom userCodeRoom);

    void delete(UserCodeRoom userCodeRoom);
}
