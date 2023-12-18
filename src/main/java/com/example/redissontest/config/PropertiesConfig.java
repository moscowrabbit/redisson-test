package com.example.redissontest.config;

import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/10/19 14:24
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Configuration
@PropertySource("test.properties")
public class PropertiesConfig extends PropertySourcesPlaceholderConfigurer {

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                     ConfigurablePropertyResolver propertyResolver) throws BeansException {
        super.processProperties(beanFactoryToProcess, propertyResolver);

        System.out.println("怎么关闭了接口的 : " + propertyResolver.getProperty("test"));
    }
}
