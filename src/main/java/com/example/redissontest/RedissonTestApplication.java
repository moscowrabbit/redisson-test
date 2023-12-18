package com.example.redissontest;

import com.example.redissontest.message.EnableRedisMessageLisenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableRedisMessageLisenter(basePackage = "com.example.redissontest.message")
public class RedissonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonTestApplication.class, args);
    }

}
