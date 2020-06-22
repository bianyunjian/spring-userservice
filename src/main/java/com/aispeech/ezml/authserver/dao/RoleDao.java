package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return {@link Role}
     */
    Role findRoleByUserId(@Param("userId") Integer userId);

}
