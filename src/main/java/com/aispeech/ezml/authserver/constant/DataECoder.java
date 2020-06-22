package com.aispeech.ezml.authserver.constant;

import com.aispeech.ezml.authserver.support.ErrorCoder;

/**
 * @author ZhangXi
 */
public enum DataECoder implements ErrorCoder {

    /**
     * 无效用户数据错误码
     */
    USER_NOT_EXIST(100101, "用户数据不存在"),
    USER_REPEATED(100102, "用户数据重复"),
    USER_ROLE_NOT_EXIST(100103, "用户对应角色不存在"),
    /**
     * 无效角色数据错误码
     */
    ROLE_NOT_EXIST(100201, "角色数据不存在"),
    ROLE_REPEATED(100202, "角色数据重复"),
    ROLE_PERMISSION_NOT_EXIST(100203, "角色对应权限不存在");

    private Integer errorCode;
    private String desc;

    DataECoder(Integer errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    @Override
    public Integer get() {
        return this.errorCode;
    }


}
