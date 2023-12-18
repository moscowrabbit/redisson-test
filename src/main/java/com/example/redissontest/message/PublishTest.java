package com.example.redissontest.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/9/5 15:54
 * Copyright: ©China software and Technical service Co.Ltd
 */
@EnableScheduling
@Component
public class PublishTest {

    private volatile static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    @SuppressWarnings("all")
    private RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 10000)
    public void sendMessage(){

        redisTemplate.convertAndSend("testChannel", "test1");
        redisTemplate.convertAndSend("testpattern-1", "test2");
        redisTemplate.convertAndSend("testpattern-2", "test3");
        redisTemplate.opsForValue().set(
                "TEST_" + atomicInteger.incrementAndGet(),
                "hello",3, TimeUnit.SECONDS);
    }
}
