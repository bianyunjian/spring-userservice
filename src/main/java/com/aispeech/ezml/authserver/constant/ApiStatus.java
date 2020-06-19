package com.aispeech.ezml.authserver.constant;

import com.aispeech.ezml.authserver.support.StringGetter;
import com.aispeech.ezml.authserver.support.base.BaseResponse;

/**
 * 响应状态枚举类
 * 用来匹配 {@link BaseResponse}的status属性值
 *
 * @author ZhangXi
 */
public enum ApiStatus implements StringGetter {

    /**
     * 响应状态
     */
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    UNAUTHORIZED("UNAUTHORIZED"),
    DENIED("DENIED");

    private String enumValue;

    ApiStatus(String enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public String get() {
        return this.enumValue;
    }

}
