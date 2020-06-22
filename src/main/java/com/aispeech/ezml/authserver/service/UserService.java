package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.pojo.UserDTO;
import com.aispeech.ezml.authserver.pojo.UserInfoVO;
import com.aispeech.ezml.authserver.pojo.UserProVO;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;

/**
 * 用户Service层接口
 *
 * @author ZhangXi
 */
public interface UserService {

    /**
     * 根据登录名称查询用户信息
     * @param userName 用户名称
     * @return {@link UserInfoVO}
     */
    UserInfoVO getUserInfoByLoginName(String userName);

    /**
     * 根据ID获取用户数据
     * @return {@link UserProVO}
     * @param id
     */
    UserProVO getUserById(Integer id);

    /**
     * 查询用户分页列表
     * @param pagedParams {@link PagedParams}
     * @param queryParams {@link UserQueryParams}
     * @return {@link PagedData<UserProVO>}
     */
    PagedData<UserProVO> queryPagedUsers(PagedParams pagedParams, UserQueryParams queryParams);

    /**
     * 新增用户数据
     * @param userDTO {@link UserDTO}
     * @return {@link UserProVO}
     */
    UserProVO addUser(UserDTO userDTO);

    /**
     * 修改用户数据
     * @param userDTO {@link UserDTO}
     * @return {@link UserProVO}
     */
    UserProVO updateUser(UserDTO userDTO);

    /**
     * 删除用户数据
     * @param userId 用户ID
     */
    void deleteUser(Integer userId);
}
