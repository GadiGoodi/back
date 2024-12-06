package com.gagoo.thiscoding.global.s3.infrastructure;

import com.gagoo.thiscoding.global.s3.service.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {
    public String random() {
        return UUID.randomUUID().toString();
    }
}