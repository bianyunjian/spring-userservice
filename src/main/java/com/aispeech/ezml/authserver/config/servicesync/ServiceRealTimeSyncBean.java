package com.aispeech.ezml.authserver.config.servicesync;

import cn.hutool.json.JSONUtil;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Import(cn.hutool.extra.spring.SpringUtil.class)
@Component
public class ServiceRealTimeSyncBean implements DisposableBean {
    String serviveName = "auth-server";


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void destroy() throws Exception {

        System.out.println(serviveName + "结束");

        //客户端主动通知注册中心下线，下线后不会再注册到eureka了
        DiscoveryManager.getInstance().shutdownComponent();
        System.out.println("客户端主动通知注册中心下线");

        //客户端发送下线消息， 所有订阅消息的外部系统会收到该消息，进行对应的处理
        ServiceSyncInfo info = new ServiceSyncInfo();
        info.setServiceName(serviveName);

        String msg = JSONUtil.toJsonStr(info);
        System.out.println("客户端发送下线消息:" + msg);
        redisTemplate.convertAndSend(RedisSubPubConfiguration.MESSAGE_CHANNEL_SERVICE_SYNC, msg);

        System.out.println("客户端发送下线消息成功！");
    }


    @Bean
    public ServerListUpdater ribbonServerListUpdater() {

        return new EurekaNotificationServerListUpdater();
    }
}
