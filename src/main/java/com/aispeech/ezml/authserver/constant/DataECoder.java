package com.aispeech.ezml.authserver.constant;

import com.aispeech.ezml.authserver.support.ErrorCoder;

/**
 * 实体数据错误码枚举
 *
 * @author ZhangXi
 */
public enum DataECoder implements ErrorCoder {

    /**
     * 无效用户数据错误码
     */
    USER_NOT_EXIST(100101, "用户数据不存在"),
    USER_REPEATED(100102, "用户数据重复"),
    USER_ROLE_NOT_EXIST(100103, "用户对应角色不存在"),
    USER_DISABLED(100104, "用户已禁用"),
    /**
     * 无效角色数据错误码
     */
    ROLE_NOT_EXIST(100201, "角色数据不存在"),
    ROLE_REPEATED(100202, "角色数据重复"),
    ROLE_PERMISSION_NOT_EXIST(100203, "角色对应权限不存在"),
    ROLE_IS_DEFAULT(100204, "默认角色禁止修改"),
    ROLE_HAS_USERS(100205, "角色已关联用户"),
    /**
     * 权限数据错误码
     */
    PERMISSION_NOT_EXIST(100301, "权限数据不存在"),
    PERMISSION_REPEATED(100302, "权限数据重复"),
    PERMISSION_HAS_ROLES(100303, "权限已关联角色");

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
