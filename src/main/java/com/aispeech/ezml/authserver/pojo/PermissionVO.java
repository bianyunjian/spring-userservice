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

    public PermissionVO() {
    }

    public PermissionVO(Permission permission) {
        this.setId(permission.getId());
        this.setPermissionName(permission.getPermissionName());
    }
}
