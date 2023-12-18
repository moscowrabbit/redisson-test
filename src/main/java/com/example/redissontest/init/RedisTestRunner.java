package com.example.redissontest.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/5 15:08
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Slf4j
@Component
public class RedisTestRunner implements ApplicationRunner {

    private RedisTemplate redisTemplate;

    @Value("${test}")
    private String userName;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("准备往redis中插入数据....." + userName);
        //redisTemplate.opsForValue().set("zhangsan","zhangsan123456");
        log.info("获取redis中保存的数据: " + redisTemplate.opsForValue().get("zhangsan"));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }
}
