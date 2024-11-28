package com.gagoo.thiscoding.domain.maria.usercoderoom.service.port;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface UserCodeRoomRepository {

    Page<UserCodeRoom> findByUserId(Long userId, Pageable pageable);

    Optional<UserCodeRoom> findById(Long codeRoomId);

    UserCodeRoom save(UserCodeRoom userCodeRoom);

    void delete(Long codeRoomId);
}
