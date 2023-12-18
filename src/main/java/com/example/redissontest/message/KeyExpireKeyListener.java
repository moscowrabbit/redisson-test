package com.example.redissontest.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/9/5 17:30
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Slf4j
@RedisMessageListener(topicName = "*:expired", topicType = TopicTypeEnum.PATTERN)
public class KeyExpireKeyListener extends RedisMessageAdaptorImpl implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info(" KeyExpireKeyListener监听到过期key ： " + new String(message.getBody()));
    }
}
