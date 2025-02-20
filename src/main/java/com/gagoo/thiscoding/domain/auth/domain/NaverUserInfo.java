package com.gagoo.thiscoding.domain.auth.domain;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("email");
    }
    @Override
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("nickname"); }

}