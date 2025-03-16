package com.gagoo.thiscoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories(basePackages = "com.gagoo.thiscoding.domain.mongo")
public class ThiscodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThiscodingApplication.class, args);
    }

}
