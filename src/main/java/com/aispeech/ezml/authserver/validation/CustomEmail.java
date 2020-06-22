package com.aispeech.ezml.authserver.validation;

import com.aispeech.ezml.authserver.validation.validator.CustomEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义邮箱校验
 *
 * @author ZhangXi
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomEmailValidator.class)
public @interface CustomEmail {

    String message() default "{app.validator.constraints.custom.email.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
