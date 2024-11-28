package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyPageService {

    Page getUserCodeRooms(Long id, Pageable pageable);

    UserCodeRoom acceptCodeRoom(Long id);

    void cancelCodeRoom(Long id);
}
