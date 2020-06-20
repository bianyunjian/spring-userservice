package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface PermissionDao extends BaseMapper<Permission> {

    /**
     * 根据ID查询权限数据
     * @param id 权限ID
     * @return {@link List<PermissionVO>}
     */
    List<PermissionVO> findListByRoleId(@Param("id") Integer id);






}
