package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
