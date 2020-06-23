package com.aispeech.ezml.authserver.model;

import lombok.Data;

/**
 * 权限表实体类
 *
 * @author ZhangXi
 */
@Data
public class Permission {

    private Integer id;

    private String permissionName;

    /**
     * 权限是否可配置：0-不可配置,1-可配置
     */
    private Integer isAssigned;

    /**
     * 权限类型：0-全部,1-仅用户界面
     */
    private Integer type;

}
