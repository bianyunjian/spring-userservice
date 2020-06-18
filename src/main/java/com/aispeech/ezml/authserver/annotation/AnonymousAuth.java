package com.aispeech.ezml.authserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略鉴权，匿名可访问的注解
 *
 * @author ZhangXi
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAuth {

    boolean required() default true;

}
