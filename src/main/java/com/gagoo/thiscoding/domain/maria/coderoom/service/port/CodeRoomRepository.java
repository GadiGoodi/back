package com.gagoo.thiscoding.domain.maria.coderoom.service.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitationCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeRoomRepository {
    CodeRoom save(CodeRoom codeRoom);
    Optional<CodeRoom> findByUuid(String uuid);
    Optional<CodeRoom> findById(Long id);
    void deleteById(Long id);
    Page<InvitationCodeRoom> findInvitedCodeRoomsByUser(User user, Pageable pageable);
}
