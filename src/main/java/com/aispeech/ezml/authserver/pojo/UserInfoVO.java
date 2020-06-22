package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户数据适配Gateway封装
 *
 * @author ZhangXi
 */
@Schema(description = "用户信息数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoVO extends UserProVO {

    @Schema(description = "用户ID", example = "1")
    private String userId;

    public UserInfoVO(User user, RoleVO role) {
        super(user, role);
        this.userId = user.getId().toString();
    }

    @Hidden
    @JsonIgnore
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Hidden
    @JsonIgnore
    @Override
    public void setId(Integer id) {
        super.setId(id);
    }
}
