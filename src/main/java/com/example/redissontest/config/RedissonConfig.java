package com.example.redissontest.config;

import com.example.redissontest.message.RedisMessageListenerWrapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/5 15:25
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Configuration
public class RedissonConfig implements ApplicationContextAware {

    @Autowired
    @SuppressWarnings("all")
    private RedisConnectionFactory redisConnectionFactory;

    private ApplicationContext applicationContext;

    @Bean
    public RedissonClient getRedissonClient(){
        List<String> nodes = Arrays.asList("redis://192.168.126.11:6379","redis://192.168.126.11:6380",
                                           "redis://192.168.126.12:6379","redis://192.168.126.12:6380",
                                           "redis://192.168.126.13:6379","redis://192.168.126.13:6380");
        Config config = new Config();
        config.useClusterServers().setTimeout(6000)
                .setRetryAttempts(3)
                .setClientName("redissonCLient")
                .setConnectTimeout(60000)
                .setTimeout(60000)
                .setScanInterval(1000)
                .setSubscriptionConnectionPoolSize(100)
                .setSubscriptionConnectionMinimumIdleSize(100)
                .setMasterConnectionPoolSize(20)
                .setSlaveConnectionPoolSize(20)
                .setMasterConnectionMinimumIdleSize(5)
                .setSlaveConnectionMinimumIdleSize(5)
                .setNodeAddresses(nodes);
        return Redisson.create(config);
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(){

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){

        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1l))
                .serializeKeysWith(RedisSerializationContext.string().getKeySerializationPair())
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean("text_channel_1")
    public RedisMessageListenerContainer redisMessageListenerContainer(){

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.setMessageListeners(selectMessageListener());
        return redisMessageListenerContainer;
    }


    /**
     * 查询所有符合要求的类
     *
     * @return Map<MessageListener, Collection<? extends Topic>>
     */
    public Map<MessageListener, Collection<? extends Topic>> selectMessageListener(){
        return applicationContext.getBeansOfType(
                RedisMessageListenerWrapper.class).values().stream().collect(
                Collectors.toMap(RedisMessageListenerWrapper::
                        getMessageListener, RedisMessageListenerWrapper::getTopics,(kq,k2)->k2));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
