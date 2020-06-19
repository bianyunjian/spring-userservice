package com.aispeech.ezml.authserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户角色关联表实体类
 *
 * @author ZhangXi
 */
@Data
public class UserRole {

    @TableId(type = IdType.UUID)
    private String id;

    private Integer userId;

    private Integer roleId;

}
