package com.example.redissontest.message;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/9/5 10:04
 * Copyright: ©China software and Technical service Co.Ltd
 */
public abstract class RedisMessageAdaptorImpl implements MessageListener, RedisMessageAdaptor {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        this.onMessage(message);
    }
}
