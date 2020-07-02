package com.aispeech.ezml.authserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author ZhangXi
 */
//@Slf4j
//@EnableCaching
//@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                // 失效时间
//                .entryTtl(Duration.ofSeconds(60))
//                // 设置key序列化方式
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // 设置value序列化方式
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                // 不缓存null
//                .disableCachingNullValues();
//        RedisCacheManager manager = RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                //
//                .transactionAware()
//                .build();
//        log.info("自定义Redis Cache加载完成");
//        return manager;
//    }


}
