package com.aispeech.ezml.authserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置
 *
 * @author ZhangXi
 */
@Slf4j
@Configuration
public class RedisConfiguration {


    @Bean(name = "redisTemplate")
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

}
