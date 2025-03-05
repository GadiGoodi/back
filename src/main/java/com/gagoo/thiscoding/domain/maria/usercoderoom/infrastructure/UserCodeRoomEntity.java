package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure;

import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.CodeRoomEntity;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_code_room")
public class UserCodeRoomEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code_room_id")
    private Long id;

    @Column(name = "is_activated")
    private boolean isActivated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_room_id")
    private CodeRoomEntity codeRoom;

    public UserCodeRoom toModel() {
        return UserCodeRoom.builder()
                .id(this.id)
                .isActivated(this.isActivated)
                .user(this.user.toModel())
                .codeRoom(this.codeRoom.toModel())
                .build();
    }

    public static UserCodeRoomEntity from(UserCodeRoom userCodeRoom) {
        UserCodeRoomEntity userCodeRoomEntity = new UserCodeRoomEntity();
        userCodeRoomEntity.id = userCodeRoom.getId();
        userCodeRoomEntity.isActivated = userCodeRoom.isActivated();
        userCodeRoomEntity.user = UserEntity.from(userCodeRoom.getUser());
        userCodeRoomEntity.codeRoom = CodeRoomEntity.from(userCodeRoom.getCodeRoom());
        return userCodeRoomEntity;
    }

}