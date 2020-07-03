package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.pojo.*;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;

import java.util.List;

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
    UserInfoVO getUserInfoByLoginName(String userName) throws InvalidDataException;

    /**
     * 根据ID获取用户数据
     * @return {@link UserProVO}
     * @param id
     */
    UserProVO getUserById(Integer id) throws InvalidDataException;

    /**
     * 根据ID数组获取用户列表
     * @return {@link List<UserVO>}
     */
    List<UserNameVO> getUsersByIds(List<Integer> ids);


    /**
     * 获取所有用户
     * @return {@link List<UserVO>}
     */
    List<UserNameVO> getAllUsers();


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
    UserProVO addUser(UserDTO userDTO) throws InvalidDataException;

    /**
     * 修改用户数据
     * @param userDTO {@link UserDTO}
     * @return {@link UserProVO}
     */
    UserProVO updateUser(UserDTO userDTO) throws InvalidDataException;

    /**
     * 删除用户数据
     * @param userId 用户ID
     */
    void deleteUser(Integer userId) throws InvalidDataException;

    /**
     * 启用用户
     * @param userId 用户ID
     */
    void enableUser(Integer userId) throws InvalidDataException;

    /**
     * 禁用用户
     * @param userId 用户ID
     */
    void disableUser(Integer userId) throws InvalidDataException;
}
