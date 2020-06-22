package com.aispeech.ezml.authserver.annotation;

import java.lang.annotation.*;

/**
 * API接口版本注解
 *
 * @author ZhangXi
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 大版本标识号，从 <code>1<code/> 开始
     */
    int value() default 1;

}
