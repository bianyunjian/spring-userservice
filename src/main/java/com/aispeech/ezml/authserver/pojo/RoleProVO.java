package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 带权限的角色VO
 *
 * @author ZhangXi
 */
@Schema(name = "RolePro", description = "带权限的角色数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleProVO extends RoleVO {

    @Schema(description = "该角色拥有的权限数据列表")
    private List<PermissionVO> permissions;

    public RoleProVO() {
    }

    public RoleProVO(Role role, List<PermissionVO> permissions) {
        super(role);
        if (null != permissions && !permissions.isEmpty()) {
            this.permissions = permissions;
        }
    }

}
