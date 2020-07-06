package com.aispeech.ezml.authserver.config.servicesync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisSubPubConfiguration {


    public static final String MESSAGE_CHANNEL_SERVICE_SYNC = "ServiceRealTimeSync";

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container2(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅多个频道
        container.addMessageListener(listenerAdapter, new PatternTopic(MESSAGE_CHANNEL_SERVICE_SYNC));
 
        return container;
    }

    /**
     * 表示监听一个频道
     *
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceive4ServiceSync receiver) {
        //传入一个消息接受的处理器，利用反射的方法调用其中的方法
        return new MessageListenerAdapter(receiver, "getMessage");
    }

//
}
