package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ParticipationsResponse {
    private Long userCodeRoomId;
    private Long codeRoomId;
    private String title;
    private String content;
    private String language;
    private List<User> user;
    private int headCount;

//    @Builder
//    public static class ProfileResponse {
//        private Long userId;
//        private String imageUrl;
//
//        public static ProfileResponse from(User user) {
//            return ProfileResponse.builder()
//                    .userId(user.getId())
//                    .imageUrl(user.getImageUrl())
//                    .build();
//        }
//    }

    @Builder
    public static ParticipationsResponse from(UserCodeRoom userCodeRoom, List<User> userList) {
        CodeRoom codeRoom = userCodeRoom.getCodeRoom();

//        List<ProfileResponse> profileList = userList.stream()
//                .map(ProfileResponse::from)
//                .toList();

        return ParticipationsResponse.builder()
                .userCodeRoomId(userCodeRoom.getId())
                .codeRoomId(codeRoom.getId())
                .title(codeRoom.getTitle())
                .content(codeRoom.getContent())
                .language(codeRoom.getLanguage())
                .user(userList)
                .headCount(codeRoom.getHeadCount())
                .build();
    }

}
