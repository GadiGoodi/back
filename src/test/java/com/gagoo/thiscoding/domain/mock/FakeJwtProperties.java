package com.gagoo.thiscoding.domain.mock;

public class FakeJwtProperties {

    public Long getAtkExpireTime() {
        return 1000 * 60 * 30L;
    }

    public Long getRtkExpireTime() {
        return  1000 * 60 * 60 * 24 * 7L;
    }
}
