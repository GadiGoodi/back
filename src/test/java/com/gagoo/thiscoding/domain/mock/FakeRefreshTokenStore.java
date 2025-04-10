package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeRefreshTokenStore implements RefreshTokenStore {

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    /**
     * 로그인할 때 리프레쉬 토큰 저장
     */
    @Override
    public void storeToken(String username, String rtk) {
        tokenStore.put(username, rtk);
    }

    /**
     * 저장된 리프레쉬 토큰과 같은지 확인
     */
    @Override
    public String getRtk(String key) {
        return tokenStore.get(key);
    }

    /**
     * 저장된 토큰 삭제
     */
    @Override
    public void remove(String email) {
        tokenStore.remove(email);
    }

    /**
     * 테스트 헬퍼 메서드
     */
    public boolean hasToken(String email) {
        return tokenStore.containsKey(email);
    }

    public void clear() {
        tokenStore.clear();
    }
}