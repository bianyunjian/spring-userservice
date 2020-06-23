package com.aispeech.ezml.authserver.model;

import com.aispeech.ezml.authserver.menum.RoleStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表实体类
 *
 * @author ZhangXi
 */
@Data
public class Role {

    public static final String COL_GMT_CREATE = "gmt_create";
    public static final String COL_ROLE_NAME = "role_name";

    public static final int IS_DEFAULT_TRUE = 1;
    public static final int IS_DEFAULT_FALSE = 0;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;

    /**
     * 角色状态：0-正常，1-禁用
     */
    private Integer status;

    /**
     * 是否为默认角色：0-否，1-是
     */
    private Integer isDefault;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;

}
