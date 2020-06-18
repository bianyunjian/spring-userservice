package com.aispeech.ezml.authserver.validation;

import com.aispeech.ezml.authserver.validation.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 手机号参数校验
 *
 * @author ZhangXi
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

    String message() default "{app.validator.constraints.mobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
