package com.aispeech.ezml.authserver.config;

import com.aispeech.ezml.authserver.constant.AppConst;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * API文档
 *
 * @author ZhangXi
 */
@OpenAPIDefinition(
        info = @Info(
                title = "AISPEECH EZML AUTH SERVER API",
                version = "v0.0.1",
                description = "思必驰标注训练一体化平台 -- 认证服务"
        )
)
@Configuration
@Profile({AppConst.PROFILE_DEV, AppConst.PROFILE_TEST})
public class ApiDocConfiguration {
}
