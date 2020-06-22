package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.constant.DataECoder;
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
import com.aispeech.ezml.authserver.service.UserService;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public UserInfoVO getUserInfoByLoginName(String userName) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq(User.COL_LOGIN_NAME, userName);
        User user = userDao.selectOne(userWrapper);
        if (null == user) {
            return null;
        }
        Role role = roleDao.findRoleByUserId(user.getId());
        return new UserInfoVO(user, new RoleVO(role));
    }

    @Override
    public UserProVO getUserById(Integer id) {
        if (null == id) {
            return null;
        }
        User user = userDao.selectById(id);
        if (null == user) {
            return null;
        }
        Role role = roleDao.findRoleByUserId(user.getId());
        return new UserProVO(user, new RoleVO(role));
    }

    @Override
    public PagedData<UserProVO> queryPagedUsers(PagedParams pagedParams, UserQueryParams queryParams) {
        long total = userDao.queryUserCountWithParams(queryParams);
        PagedData<UserProVO> data = new PagedData<>();
        data.setTotal(total);
        if (total > 0) {
            int start = (pagedParams.getPageNum()-1) * pagedParams.getPageSize();
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
            throw new InvalidDataException("用户登录名重复，loginName="+user.getLoginName()).with(DataECoder.USER_REPEATED);
        }
        userDao.insert(user);
        Integer roleId = userDTO.getRoleId();
        Role role = roleDao.selectById(roleId);
        // 检查角色是否存在
        if (null == role) {
            throw new InvalidDataException("用户对应角色不存在，roleId="+roleId).with(DataECoder.USER_ROLE_NOT_EXIST);
        }
        userRoleDao.insert(new UserRole(user.getId(), role.getId()));
        return new UserProVO(user, new RoleVO(role));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProVO updateUser(UserDTO userDTO) throws InvalidDataException {
        User user = userDTO.getUser();
        User oldOne = userDao.selectById(user.getId());
        // fixme 用户名可以重复么?
        if (null == oldOne) {
            throw new InvalidDataException("用户不存在，id="+user.getId()).with(DataECoder.USER_NOT_EXIST);
        } else if (oldOne.getStatus().equals(UserStatus.DISABLED.getDbValue())) {
            throw new InvalidDataException("用户已禁用，id="+user.getId()).with(DataECoder.USER_DISABLED);
        }
        Integer roleId = userDTO.getRoleId();
        Role role = roleDao.selectById(roleId);
        if (null == role) {
            throw new InvalidDataException("用户对应角色不存在，roleId="+roleId).with(DataECoder.USER_ROLE_NOT_EXIST);
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, user.getId());
        userRoleDao.delete(userRoleQueryWrapper);
        userRoleDao.insert(new UserRole(user.getId(), role.getId()));
        return new UserProVO(user, new RoleVO(role));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在，id="+userId).with(DataECoder.USER_NOT_EXIST);
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, userId);
        userRoleDao.delete(userRoleQueryWrapper);
        userDao.deleteById(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void enableUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在，id="+userId).with(DataECoder.USER_NOT_EXIST);
        }
        // 启用用户
        user.setStatus(UserStatus.NORMAL.getDbValue());
        userDao.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void disableUser(Integer userId) throws InvalidDataException {
        User user = null;
        if (null != userId) {
            user = userDao.selectById(userId);
        }
        if (null == user) {
            throw new InvalidDataException("用户不存在，id="+userId).with(DataECoder.USER_NOT_EXIST);
        }
        // 禁用用户
        user.setStatus(UserStatus.DISABLED.getDbValue());
        userDao.updateById(user);
    }
}
