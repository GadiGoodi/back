package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeRoomCustomRepository {
    Page<InvitedCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable);
}
