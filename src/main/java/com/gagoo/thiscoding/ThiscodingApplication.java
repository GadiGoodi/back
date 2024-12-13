package com.gagoo.thiscoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
public class ThiscodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThiscodingApplication.class, args);
    }

}
