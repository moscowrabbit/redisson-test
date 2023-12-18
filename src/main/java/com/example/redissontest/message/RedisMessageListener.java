package com.example.redissontest.message;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Description: redis监听器
 * @author: 丁波琪 <dingboqi@css.com.cn>
 * @date: 2023/9/5 10:03
 * @Copyright: ©China software and Technical service Co.Ltd
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisMessageListener {

    String value() default "";

    TopicTypeEnum topicType() default TopicTypeEnum.CHANNEL;

    String topicName() default "";
}
