package com.aispeech.ezml.authserver.menum;

/**
 * 权限类型枚举
 *
 * @author ZhangXi
 */
public enum PermissionType {

    /**
     * 权限类型
     */
    PAGE(1, "页面权限"),
    API(2, "接口权限");

    private Integer typeValue;
    private String desc;

    PermissionType(Integer typeValue, String desc) {
        this.typeValue = typeValue;
        this.desc = desc;
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public String getDesc() {
        return desc;
    }
}
