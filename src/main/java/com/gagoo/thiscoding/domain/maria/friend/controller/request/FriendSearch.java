package com.gagoo.thiscoding.domain.maria.friend.controller.request;

import jakarta.validation.constraints.NotBlank;

public record FriendSearch(@NotBlank String keyword) {

}
