package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.Permission;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 权限VO
 *
 * @author ZhangXi
 */
@Schema(name = "Permission", description = "权限数据")
public class PermissionVO extends Permission {

    @Schema(description = "权限ID")
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Schema(description = "权限名称")
    @Override
    public String getPermissionName() {
        return super.getPermissionName();
    }

    @Schema(description = "是否可分配，0-否，1-是")
    @Override
    public Integer getIsAssigned() {
        return super.getIsAssigned();
    }

    @Schema(description = "权限类型")
    @Override
    public Integer getType() {
        return super.getType();
    }

    public PermissionVO() {
    }

    public PermissionVO(Permission permission) {
        if (null != permission) {
            this.setId(permission.getId());
            this.setPermissionName(permission.getPermissionName());
            this.setIsAssigned(permission.getIsAssigned());
            this.setType(permission.getType());
        }
    }



}
