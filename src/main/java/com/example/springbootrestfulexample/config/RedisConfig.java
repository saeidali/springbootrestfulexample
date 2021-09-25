package com.example.springbootrestfulexample.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Value("${spring.redis.host}")
    public String redisHost;

    @Value("${spring.redis.port}")
    public int redisPort;

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
            configurationMap.put("usersList", RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(10)));
            configurationMap.put("userInfo", RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(20)));
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

}
