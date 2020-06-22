package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.dao.RoleDao;
import com.aispeech.ezml.authserver.dao.UserDao;
import com.aispeech.ezml.authserver.dao.UserRoleDao;
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
        IPage<User> userPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userPage = userDao.selectPage(userPage, userQueryWrapper);
        long total = userPage.getTotal();
        PagedData<UserProVO> data = new PagedData<>();
        data.setTotal(total);
        if (total > 0) {
            List<User> userList = userPage.getRecords();
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
    public UserProVO addUser(UserDTO userDTO) {
        //todo 前置检查
        User user = userDTO.getUser();
        userDao.insert(user);
        Integer roleId = userDTO.getRoleId();
        Role existedRole = roleDao.selectById(roleId);
        if (null == existedRole) {
            return null;
        }
        userRoleDao.insert(new UserRole(user.getId(), existedRole.getId()));
        return new UserProVO(user, new RoleVO(existedRole));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProVO updateUser(UserDTO userDTO) {
        User user = userDTO.getUser();
        User oldOne = userDao.selectById(user.getId());
        if (null == oldOne) {
            return null;
        }
        Integer roleId = userDTO.getRoleId();
        Role role = roleDao.selectById(roleId);
        if (null == role) {
            return null;
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, user.getId());
        userRoleDao.delete(userRoleQueryWrapper);
        userRoleDao.insert(new UserRole(user.getId(), role.getId()));
        return new UserProVO(user, new RoleVO(role));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Integer userId) {
        if (null == userId) {
            return;
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq(UserRole.COL_USER_ID, userId);
        userRoleDao.delete(userRoleQueryWrapper);
        userDao.deleteById(userId);
    }
}
