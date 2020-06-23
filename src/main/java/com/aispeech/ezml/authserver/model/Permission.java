package com.aispeech.ezml.authserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 权限表实体类
 *
 * @author ZhangXi
 */
@Data
public class Permission {

    public static final String COL_PERMISSION_NAME = "permission_name";

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String permissionName;

    /**
     * 权限是否可配置：0-不可配置,1-可配置
     */
    private Integer isAssigned;

    /**
     * 权限类型：1-页面,2-接口
     */
    private Integer type;

}
