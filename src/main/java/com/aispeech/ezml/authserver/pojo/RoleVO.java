package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.constant.AppConst;
import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.validation.group.GUpd;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 角色VO
 *
 * @author ZhangXi
 */
@Schema(name = "Role", description = "角色数据")
public class RoleVO extends Role {

    @NotNull(groups = {GUpd.class})
    @Schema(description = "角色ID", example = "1", format = "null或者非负整数")
    @Override
    public Integer getId() {
        return super.getId();
    }

    @NotBlank
    @Length(min = 2, max = 20)
    @Schema(description = "角色名称", example = "管理员", format = "必须为6~20位字母、数字组合")
    @Override
    public String getRoleName() {
        return super.getRoleName();
    }

    @Schema(description = "角色状态", example = "0", format = "取值列表：[0,1]")
    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    @Schema(description = "是否为默认角色：0-否，1-是", example = "1")
    @Override
    public Integer getIsDefault() {
        return super.getIsDefault();
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConst.BASIC_TIME_FORMAT)
    @Schema(description = "角色创建时间")
    @Override
    public LocalDateTime getGmtCreate() {
        return super.getGmtCreate();
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConst.BASIC_TIME_FORMAT)
    @Schema(description = "角色更新时间")
    @Override
    public LocalDateTime getGmtUpdate() {
        return super.getGmtUpdate();
    }

    public RoleVO() {
    }

    public RoleVO(Role role) {
        if (null != role) {
            this.setId(role.getId());
            this.setRoleName(role.getRoleName());
            this.setStatus(role.getStatus());
            this.setIsDefault(role.getIsDefault());
            this.setGmtCreate(role.getGmtCreate());
            this.setGmtUpdate(role.getGmtUpdate());
        }
    }
}
