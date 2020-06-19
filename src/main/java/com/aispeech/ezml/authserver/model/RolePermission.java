package com.aispeech.ezml.authserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 角色权限关联表实体类
 *
 * @author ZhangXi
 */
@Data
public class RolePermission {

    @TableId(type = IdType.UUID)
    private String id;

    private Integer roleId;

    private Integer permissionId;

}
