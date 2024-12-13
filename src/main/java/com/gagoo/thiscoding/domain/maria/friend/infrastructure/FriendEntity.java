package com.gagoo.thiscoding.domain.maria.friend.infrastructure;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "friend")
public class FriendEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @Column(name = "is_friend")
    private boolean isFriend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    public static FriendEntity from(Friend friend) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.id = friend.getId();
        friendEntity.isFriend = friend.isFriend();
        friendEntity.sender = UserEntity.from(friend.getSender());
        friendEntity.receiver = UserEntity.from(friend.getReceiver());
        return friendEntity;
    }

    public Friend toModel() {
        return Friend.builder()
            .id(id)
            .isFriend(isFriend)
            .sender(sender.toModel())
            .receiver(receiver.toModel())
            .build();
    }
}
