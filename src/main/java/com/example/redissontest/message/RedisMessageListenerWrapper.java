package com.example.redissontest.message;

import lombok.Data;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;

import java.util.Collection;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/9/5 14:47
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Data
public class RedisMessageListenerWrapper {

    private MessageListener messageListener;

    private Collection<? extends Topic> topics;
}
