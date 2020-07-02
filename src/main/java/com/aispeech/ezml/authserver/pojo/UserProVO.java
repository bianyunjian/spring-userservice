package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 带角色用户VO
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserProVO extends UserVO {

    @Schema(description = "用户对应角色数据")
    private RoleVO role;

    @Schema(description = "用户权限列表")
    private List<PermissionVO> permissionList;

    public UserProVO() {
    }

    public UserProVO(User user, RoleVO role) {
        super(user);
        this.role = role;
    }
}
