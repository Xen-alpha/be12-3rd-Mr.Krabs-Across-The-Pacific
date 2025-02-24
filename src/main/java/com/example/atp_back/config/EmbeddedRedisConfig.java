package com.example.atp_back.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
@Profile("embedded-redis") // "embedded-redis" 프로파일에서만 활성화
@EnableRedisRepositories
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
        System.out.println("Embedded Redis 서버가 시작되었습니다.");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            System.out.println("Embedded Redis 서버가 종료되었습니다.");
        }
    }
}