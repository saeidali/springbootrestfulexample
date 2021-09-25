package com.example.springbootrestfulexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCaching
public class SpringbootrestfulexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootrestfulexampleApplication.class, args);
    }

}
