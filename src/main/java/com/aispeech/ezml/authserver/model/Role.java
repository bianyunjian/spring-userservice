package com.aispeech.ezml.authserver.model;

import com.aispeech.ezml.authserver.menum.RoleStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 角色表实体类
 *
 * @author ZhangXi
 */
@Data
public class Role {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private RoleStatus status;

}
