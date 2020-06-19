package com.aispeech.ezml.authserver.menum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 角色状态枚举
 *
 * @author ZhangXi
 */
public enum RoleStatus {

    /**
     * 角色状态
     */
    NORMAL(0, "正常"),
    DISABLED(1, "禁用");

    @EnumValue
    @JsonValue
    private Integer dbValue;

    private String desc;

    RoleStatus(Integer dbValue, String desc) {
        this.dbValue = dbValue;
        this.desc = desc;
    }

    public Integer getDbValue() {
        return dbValue;
    }

    public String getDesc() {
        return desc;
    }
}
