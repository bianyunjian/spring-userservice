package com.aispeech.ezml.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 *
 *
 * @author ZhangXi
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class
})
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
