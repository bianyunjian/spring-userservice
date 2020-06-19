package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface PermissionDao extends BaseMapper<Permission> {
}
