package com.gagoo.thiscoding.domain.maria.reply.infrastructure.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(value = {"test"})
@Sql("/sql/reply-repository-test-data.sql")
class ReplyRepositoryTest {

    @Test
    void findByQnaId() {

    }
}