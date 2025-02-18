package com.gagoo.thiscoding.domain.auth.domain;

public interface OAuth2UserInfo {
    String getProvider();
    String getEmail();
    String getNickname();
}
