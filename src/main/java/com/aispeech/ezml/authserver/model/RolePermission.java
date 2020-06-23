package com.aispeech.ezml.authserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.UUID;

/**
 * 角色权限关联表实体类
 *
 * @author ZhangXi
 */
@Data
public class RolePermission {

    public static final String COL_PERMISSION_ID = "permission_id";

    private String id;

    private Integer roleId;

    private Integer permissionId;

    public RolePermission(Integer roleId, Integer permissionId) {
        this.id = UUID.randomUUID().toString();
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
