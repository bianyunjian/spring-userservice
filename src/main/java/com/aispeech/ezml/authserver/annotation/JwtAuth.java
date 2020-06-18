package com.aispeech.ezml.authserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JWT 鉴权验证通过后才能访问的注解
 *
 * @author ZhangXi
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAuth {

    String value();

    boolean required() default true;

}
