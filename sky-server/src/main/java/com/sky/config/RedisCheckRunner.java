package com.sky.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisCheckRunner implements CommandLineRunner {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Override
    public void run(String... args) {
        System.out.println(">>> Redis Host = " + redisHost);
        System.out.println(">>> Redis Password = " + redisPassword);
    }
}
