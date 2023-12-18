package com.example.redissontest.message;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/9/5 14:04
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class RedisMessageListenerRegister
        implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    /**
     * Environment
     */
    private Environment environment;

    /**
     * ResourceLoader
     */
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                 BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        //获取注解中配置的基础包
        Map<String,Object> attributesMap = importingClassMetadata.getAnnotationAttributes(EnableRedisMessageLisenter.class.getName(), true);
        String basePackage = String.valueOf(attributesMap.get("basePackage"));
        //扫描包下面的所有文件获取对象
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false,environment);
        scanner.setResourceLoader(resourceLoader);
        scanner.setEnvironment(environment);
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> {
            List<String> interfaceNaame = Arrays.asList(metadataReader.getClassMetadata().getInterfaceNames());
            return interfaceNaame.contains(MessageListener.class.getName());
        });
        scanner.addIncludeFilter(new AnnotationTypeFilter(RedisMessageListener.class));
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
        for(BeanDefinition beanDefinition : beanDefinitions){
            if(beanDefinition instanceof AnnotatedBeanDefinition){
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                Map<String,Object> annotationMetadataAttributes = annotatedBeanDefinition
                        .getMetadata().getAnnotationAttributes(RedisMessageListener.class.getCanonicalName());
                TopicTypeEnum topicTypeEnum = (TopicTypeEnum) annotationMetadataAttributes.get("topicType");
                String topicName = (String) annotationMetadataAttributes.get("topicName");
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RedisMessageListenerWrapper.class);
                try {
                    Class<?> clazz = Class.forName(annotatedBeanDefinition.getMetadata().getClassName());
                    beanDefinitionBuilder.addPropertyValue("messageListener", clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Topic topic = topicTypeEnum.equals(TopicTypeEnum.CHANNEL) ? new ChannelTopic(topicName) : new PatternTopic(topicName);
                beanDefinitionBuilder.addPropertyValue("topics", Arrays.asList(topic));
                beanDefinitionBuilder.setPrimary(true);
                BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinitionBuilder.getBeanDefinition(),beanDefinition.getBeanClassName());
                BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
            }
        }
        ImportBeanDefinitionRegistrar.super
                .registerBeanDefinitions(importingClassMetadata, registry, importBeanNameGenerator);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
