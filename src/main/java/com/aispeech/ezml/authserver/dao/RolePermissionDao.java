package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermission> {
}
