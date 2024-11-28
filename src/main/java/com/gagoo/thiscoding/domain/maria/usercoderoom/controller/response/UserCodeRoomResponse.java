package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response;


import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCodeRoomResponse {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createDate;
    private Long roomId;
    private Boolean isAccepted;

    public static UserCodeRoom from(UserCodeRoom userCodeRoom) {
        return UserCodeRoom.builder()
            .id(userCodeRoom.getId())
            .user(userCodeRoom.getUser())
            .codeRoom(userCodeRoom.getCodeRoom())
            .isActivated(userCodeRoom.isActivated())
            .isAccepted(userCodeRoom.isAccepted())
            .build();
    }
}
