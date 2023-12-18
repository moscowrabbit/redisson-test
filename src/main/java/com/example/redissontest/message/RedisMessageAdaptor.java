package com.example.redissontest.message;

import org.springframework.data.redis.connection.Message;

/**
 * @Description:
 * @author: 丁波琪 <dingboqi@css.com.cn>
 * @date: 2023/9/5 10:03
 * @Copyright: ©China software and Technical service Co.Ltd
 */
public interface RedisMessageAdaptor {

    void onMessage(Message message);
}
