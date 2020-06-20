package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.Role;
import lombok.Data;

import java.util.List;

/**
 * 角色DTO
 * 传递请求数据到service层
 *
 * @author ZhangXi
 */
@Data
public class RoleDTO {

    private Role role;

    private List<Integer> permissionIds;

}
