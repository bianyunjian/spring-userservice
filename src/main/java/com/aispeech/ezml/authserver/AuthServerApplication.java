package com.aispeech.ezml.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务
 *
 * @author ZhangXi
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class
})
@EnableDiscoveryClient
public class AuthServerApplication {

    public static void main(String[] args) {
        System.out.println("auth-server开始");
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
