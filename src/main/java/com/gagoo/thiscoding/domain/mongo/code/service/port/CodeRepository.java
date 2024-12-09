package com.gagoo.thiscoding.domain.mongo.code.service.port;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import reactor.core.publisher.Mono;

public interface CodeRepository {
    Code save(Code code);
}
