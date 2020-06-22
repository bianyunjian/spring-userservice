package com.aispeech.ezml.authserver.menum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户状态
 *
 * @author ZhangXi
 */
@Schema(description = "用户状态枚举")
public enum UserStatus {

    /**
     * 用户状态
     */
    NORMAL(0, "正常"),
    DISABLED(1, "禁用");

    private Integer dbValue;
    private String desc;

    UserStatus(Integer dbValue, String desc) {
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
