package com.aispeech.ezml.authserver.validation.validator;

import com.aispeech.ezml.authserver.validation.CustomEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义邮箱校验器
 *
 * @author ZhangXi
 */
public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {

    @Override
    public void initialize(CustomEmail constraintAnnotation) {
        //todo
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //todo
        return true;
    }
}
