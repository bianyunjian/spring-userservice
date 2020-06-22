package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.constant.AppConst;
import com.aispeech.ezml.authserver.model.User;
import com.aispeech.ezml.authserver.validation.CustomEmail;
import com.aispeech.ezml.authserver.validation.group.GAdd;
import com.aispeech.ezml.authserver.validation.group.GUpd;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户VO
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {

    @NotNull(groups = {GUpd.class})
    @Schema(description = "用户ID", example = "1")
    @Override
    public Integer getId() {
        return super.getId();
    }

    @CustomEmail(groups = {GAdd.class})
    @NotBlank(groups = {GAdd.class})
    @Schema(description = "登录名，默认邮箱", example = "admin@aispeech.com")
    @Override
    public String getLoginName() {
        return super.getLoginName();
    }

    @NotBlank
    @Schema(description = "用户名称", example = "超级管理员", required = true)
    @Override
    public String getUserName() {
        return super.getUserName();
    }

    @NotBlank(groups = {GAdd.class})
    @Schema(description = "登录密码", required = true)
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Schema(description = "用户邮箱", example = "admin@aispeech.com")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @NotBlank
    @Schema(description = "用户所属部门", example = "研发部", required = true)
    @Override
    public String getDepartment() {
        return super.getDepartment();
    }

    @Schema(description = "用户职位", example = "架构师")
    @Override
    public String getPosition() {
        return super.getPosition();
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConst.BASIC_TIME_FORMAT)
    @Schema(description = "最近一次登录时间", example = "2020-06-22 14:37:20")
    @Override
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Schema(description = "用户状态，0-正常,1-禁用", example = "0")
    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    public UserVO() {}

    public UserVO(User user) {
        this.setId(user.getId());
        this.setLoginName(user.getLoginName());
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setDepartment(user.getDepartment());
        this.setEmail(user.getEmail());
        this.setPosition(user.getPosition());
        this.setLastLoginTime(user.getLastLoginTime());
        this.setStatus(user.getStatus());
    }



}
