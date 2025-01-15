package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeRoomCustomRepository {
    Page<InvitedCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable);
    List<User> findUserListByUserCodeRoom(UserCodeRoom userCodeRoom);
    Page<UserCodeRoom> findAllByEmailAndIsActivatedTrue(String email, Pageable pageable);
}
