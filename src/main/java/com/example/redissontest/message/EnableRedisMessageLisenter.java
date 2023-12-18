package com.example.redissontest.message;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisMessageListenerRegister.class})
public @interface EnableRedisMessageLisenter {

    String basePackage();
}
