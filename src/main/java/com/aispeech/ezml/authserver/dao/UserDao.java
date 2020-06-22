package com.aispeech.ezml.authserver.dao;

import com.aispeech.ezml.authserver.model.User;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 分页条件查询用户列表
     * @param queryParams {@link UserQueryParams}
     * @param start 起始位置
     * @param size 每次查询数量
     * @return {@link List<User>}
     */
    List<User> queryUserListWithParams(@Param("query") UserQueryParams queryParams, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 分页条件查询用户总数
     * @param queryParams {@link UserQueryParams}
     * @return 用户总数
     */
    long queryUserCountWithParams(@Param("query") UserQueryParams queryParams);

}
