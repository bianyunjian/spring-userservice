package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermission> {

    /**
     * 批量插入角色权限关联数据
     * @param rolePermissions {@link List<RolePermission>}
     */
    void batchInsert(List<RolePermission> rolePermissions);


    /**
     * 删除某角色关联数据
     * @param roleId 角色ID
     */
    void deleteByRoleId(@Param("id") Integer roleId);

}
