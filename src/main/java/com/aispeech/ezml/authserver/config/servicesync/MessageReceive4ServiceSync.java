package com.aispeech.ezml.authserver.config.servicesync;

import cn.hutool.extra.spring.SpringUtil;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MessageReceive4ServiceSync {
    public void getMessage(String object) {

        System.out.println("接受到消息：" + object);

        //主动刷新缓存
        refresh();
    }

    /**
     * 通过反射调用DiscoveryClient的refreshRegistry方法，此方法会触发更新事件
     * （配置好ribbon使用EurekaNotificationServerListUpdater更新）
     * 这样每次服务下线就可以通过广播消息触发ribbon更新
     */
    public void refresh() {
        try {
            DiscoveryClient bean = SpringUtil.getBean(DiscoveryClient.class);
            if (bean != null) {
                Method method = DiscoveryClient.class.getDeclaredMethod("refreshRegistry");
                method.setAccessible(true);
                method.invoke(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
