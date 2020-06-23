package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.validation.group.GAdd;
import com.aispeech.ezml.authserver.validation.group.GUpd;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限VO
 *
 * @author ZhangXi
 */
@Schema(name = "Permission", description = "权限数据")
public class PermissionVO extends Permission {

    @NotNull(groups = {GUpd.class})
    @Schema(description = "权限ID", example = "1")
    @Override
    public Integer getId() {
        return super.getId();
    }

    @NotBlank
    @Schema(description = "权限名称", example = "测试权限")
    @Override
    public String getPermissionName() {
        return super.getPermissionName();
    }

    @NotNull
    @Range(min = 0, max = 1)
    @Digits(integer = 1, fraction = 0)
    @Schema(description = "是否可分配，0-否，1-是", example = "1")
    @Override
    public Integer getIsAssigned() {
        return super.getIsAssigned();
    }

    @NotNull
    @Schema(description = "权限类型，1-页面，2-接口", example = "1")
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
