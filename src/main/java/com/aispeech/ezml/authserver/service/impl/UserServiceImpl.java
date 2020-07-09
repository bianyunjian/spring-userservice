package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.constant.DataECoder;
import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.dao.RoleDao;
import com.aispeech.ezml.authserver.dao.UserDao;
import com.aispeech.ezml.authserver.dao.UserRoleDao;
import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.menum.UserStatus;
import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.model.User;
import com.aispeech.ezml.authserver.model.UserRole;
import com.aispeech.ezml.authserver.pojo.RoleVO;
import com.aispeech.ezml.authserver.pojo.UserDTO;
import com.aispeech.ezml.authserver.pojo.UserInfoVO;
import com.aispeech.ezml.authserver.pojo.UserProVO;
import com.aispeech.ezml.authserver.service.CacheService;
import com.aispeech.ezml.authserver.pojo.*;
import com.aispeech.ezml.authserver.service.UserService;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 用户Service层实现类
 *
 * @author ZhangXi
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private PermissionDao permissionDao;
    @Autowired
    private CacheService cacheService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoVO getUserInfoByLoginName(String userName) throws InvalidDataException {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq(User.COL_LOGIN_NAME, userName);
        User user = userDao.selectOne(userWrapper);
        if (null == user) {
            throw new InvalidDataException("用户不存在,loginName=" + userName).with(DataECoder.USER_NOT_EXIST);
        }
        // 记录登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userDao.updateById(user);

        Role role = roleDao.findRoleByUserId(user.getId());
        UserInfoVO resp = new UserInfoVO(user, new RoleVO(role));
        if (role != null) {
            List<PermissionVO> permissionVOList = permissionDao.findListByRoleId(role.getId());
            resp.setPermissionList(permissionVOList);
        }
        return resp;
    }

    @Override
    public UserProVO getUserById(Integer id) throws InvalidDataException {
        User user = null;
        if (null != id) {
            user = userDao.selectById(id);
            user.setPassword("");
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在,id=" + id).with(DataECoder.USER_NOT_EXIST);
        }
        Role role = roleDao.findRoleByUserId(user.getId());


        UserProVO resp = new UserProVO(user, new RoleVO(role));
        if (role != null) {
            List<PermissionVO> permissionVOList = permissionDao.findListByRoleId(role.getId());
            resp.setPermissionList(permissionVOList);
        }
        return resp;
    }

    @Override
    public List<UserNameVO> getUsersByIds(List<Integer> ids) {
        return userDao.getEnabledListByInIds(ids);
    }

    @Override
    public List<UserNameVO> getAllUsers() {
        return userDao.getEnabledListByInIds(null);
    }

    @Override
    public PagedData<UserProVO> queryPagedUsers(PagedParams pagedParams, UserQueryParams queryParams) {
        // 校验roleId是否为空
        try {
            int idVal = Integer.parseInt(queryParams.getRoleId());
            if (idVal <= 0) {
                queryParams.setRoleId(null);
            }
        } catch (Exception e) {
            queryParams.setRoleId(null);
        }
        long total = userDao.queryUserCountWithParams(queryParams);
        PagedData<UserProVO> data = new PagedData<>();
        data.setTotal(total);
        if (total > 0) {
            int start = (pagedParams.getPageNum() - 1) * pagedParams.getPageSize();
            List<User> userList = userDao.queryUserListWithParams(queryParams, start, pagedParams.getPageSize());
            List<UserProVO> voList = new ArrayList<>(userList.size());
            for (User user : userList) {
                Role role = roleDao.findRoleByUserId(user.getId());
                voList.add(new UserProVO(user, new RoleVO(role)));
            }
            data.setList(voList);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProVO addUser(UserDTO userDTO) throws InvalidDataException {
        User user = userDTO.getUser();
        user.setStatus(UserStatus.NORMAL.getDbValue());
        // 检查登录名是否重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(User.COL_LOGIN_NAME, user.getLoginName());
        User repeatOne = userDao.selectOne(userQueryWrapper);
        if (null != repeatOne) {
            throw new InvalidDataException("用户登录名重复，loginName=" + user.getLoginName()).with(DataECoder.USER_REPEATED);
        }
        // 设置默认值
        // 密码 需要在这里base64编码？
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            String passwordBase64 = encoder.encodeToString(user.getPassword().getBytes("UTF-8"));   user.setPassword( passwordBase64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setId(null);
        user.setStatus(UserStatus.NORMAL.getDbValue());
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtUpdate(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        userDao.insert(user);

        Integer roleId = userDTO.getRoleId();
        Role role = roleDao.selectById(roleId);
        // 检查角色是否存在
        if (null == role) {
            throw new InvalidDataException("用户对应角色不存在，roleId=" + roleId).with(DataECoder.USER_ROLE_NOT_EXIST);
        }
        userRoleDao.insert(new UserRole(user.getId(), role.getId()));
        // 缓存redis
        cacheService.cacheUser(user);
        return new UserProVO(user, new RoleVO(role));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProVO updateUser(UserDTO userDTO) throws InvalidDataException {
        User user = userDTO.getUser();
        User oldOne = userDao.selectById(user.getId());
        // fixme 用户姓名是否可以重复?
        // 检查用户是否存在
        if (null == oldOne) {
            throw new InvalidDataException("用户不存在，id=" + user.getId()).with(DataECoder.USER_NOT_EXIST);
        }
        // 检查用户是否禁用
        if (oldOne.getStatus().equals(UserStatus.DISABLED.getDbValue())) {
            throw new InvalidDataException("用户已禁用，id=" + user.getId()).with(DataECoder.USER_DISABLED);
        }
        // 更新用户数据
        oldOne.setUserName(user.getUserName());
        oldOne.setDepartment(user.getDepartment());
        oldOne.setPosition(user.getPosition());
        oldOne.setGmtUpdate(LocalDateTime.now());
        userDao.updateById(oldOne);

        Integer roleId = userDTO.getRoleId();
        Role role = roleDao.selectById(roleId);
        // 检查用户对应角色是否存在
        if (null == role) {
            throw new InvalidDataException("用户对应角色不存在，roleId=" + roleId).with(DataECoder.USER_ROLE_NOT_EXIST);
        }
        // 更新用户对应角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, oldOne.getId());
        userRoleDao.delete(userRoleQueryWrapper);
        userRoleDao.insert(new UserRole(oldOne.getId(), role.getId()));
        // 缓存redis
        cacheService.cacheUser(oldOne);
        return new UserProVO(oldOne, new RoleVO(role));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        // 检查用户是否存在
        if (null == user) {
            throw new InvalidDataException("用户不存在，id=" + userId).with(DataECoder.USER_NOT_EXIST);
        }
        // 删除用户及关联角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, userId);
        userRoleDao.delete(userRoleQueryWrapper);
        userDao.deleteById(userId);
        // 清除缓存redis
        cacheService.cleanUser(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void enableUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在，id=" + userId).with(DataECoder.USER_NOT_EXIST);
        }
        // 启用用户
        user.setStatus(UserStatus.NORMAL.getDbValue());
        userDao.updateById(user);
        // 缓存redis
        cacheService.cacheUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void disableUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在，id=" + userId).with(DataECoder.USER_NOT_EXIST);
        }
        // 禁用用户
        user.setStatus(UserStatus.DISABLED.getDbValue());
        userDao.updateById(user);
        // 缓存redis
        // fixme 禁用的用户应该从缓存中移除么？
        cacheService.cacheUser(user);
    }
}
