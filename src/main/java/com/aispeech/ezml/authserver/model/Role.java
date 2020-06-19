package com.aispeech.ezml.authserver.model;

import com.aispeech.ezml.authserver.menum.RoleStatus;
import lombok.Data;

/**
 * 角色表实体类
 *
 * @author ZhangXi
 */
@Data
public class Role {

    private Integer id;

    private String roleName;

    private RoleStatus status;

}
