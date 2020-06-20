package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.pojo.RoleDTO;
import com.aispeech.ezml.authserver.pojo.RoleProVO;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.RoleQueryParams;

/**
 * 角色Service层接口
 *
 * @author ZhangXi
 */
public interface RoleService {

    /**
     * 根据ID获取带权限角色
     * @param id 角色ID
     * @return {@link RoleProVO}
     */
    RoleProVO getRoleById(Integer id);

    /**
     * 分页查询角色列表
     * @param pagedParams {@link PagedParams}
     * @param queryParams {@link RoleQueryParams}
     * @return {@link PagedData<RoleProVO>}
     */
    PagedData<RoleProVO> queryPagedRoles(PagedParams pagedParams, RoleQueryParams queryParams);

    /**
     * 新增角色
     * @param roleDTO {@link RoleDTO}
     * @return {@link RoleProVO}
     */
    RoleProVO addRole(RoleDTO roleDTO);

    /**
     * 修改角色
     * @param roleDTO {@link RoleDTO}
     * @return {@link RoleProVO}
     */
    RoleProVO updateRole(RoleDTO roleDTO);

    /**
     * 删除角色
     * @param id 角色ID
     */
    void deleteRole(Integer id);

}
