package com.aispeech.ezml.authserver.config;

import com.aispeech.ezml.authserver.bean.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置
 *
 * @author ZhangXi
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Autowired
    public MvcConfiguration(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    /**
     * 配置拦截器
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }







}
