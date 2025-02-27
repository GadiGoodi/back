package com.gagoo.thiscoding.domain.maria.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record UpdateProfileNicknameRequest(@NotBlank String nickname) {
//    @Builder
//    public UpdateProfileNicknameRequest(@JsonProperty("nickname") String nickname) {
//        this.nickname = nickname;
//    }
}
