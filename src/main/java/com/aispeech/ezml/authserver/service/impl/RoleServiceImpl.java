package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.constant.DataECoder;
import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.dao.RoleDao;
import com.aispeech.ezml.authserver.dao.RolePermissionDao;
import com.aispeech.ezml.authserver.dao.UserRoleDao;
import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.menum.RoleStatus;
import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.model.RolePermission;
import com.aispeech.ezml.authserver.model.UserRole;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.pojo.RoleDTO;
import com.aispeech.ezml.authserver.pojo.RoleProVO;
import com.aispeech.ezml.authserver.service.RoleService;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.RoleQueryParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色Service层实现类
 *
 * @author ZhangXi
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RolePermissionDao rolePermissionDao;
    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public RoleProVO getRoleById(Integer id) throws InvalidDataException {
        Role role = roleDao.selectById(id);
        if (null == role) {
            throw new InvalidDataException("角色数据不存在，id="+id).with(DataECoder.ROLE_NOT_EXIST);
        }
        List<PermissionVO> list = permissionDao.findListByRoleId(id);
        return new RoleProVO(role, list);
    }

    @Override
    public PagedData<RoleProVO> queryPagedRoles(PagedParams pagedParams, RoleQueryParams queryParams) {
        IPage<Role> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<Role> wQuery = new QueryWrapper<>();
        wQuery.orderByAsc(Role.COL_GMT_CREATE);
        iPage = roleDao.selectPage(iPage, wQuery);

        PagedData<RoleProVO> data = new PagedData<>();
        long total = iPage.getTotal();
        data.setTotal(total);
        if (total > 0) {
            List<Role> roles = iPage.getRecords();
            List<RoleProVO> voList = new ArrayList<>(roles.size());
            for (Role role : roles) {
                List<PermissionVO> permissions = permissionDao.findListByRoleId(role.getId());
                voList.add(new RoleProVO(role, permissions));
            }
            data.setList(voList);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleProVO addRole(RoleDTO roleDTO) throws InvalidDataException {
        Role role = roleDTO.getRole();
        // 检查名称是否重复
        QueryWrapper<Role> nameQuery = new QueryWrapper<>();
        nameQuery.eq(Role.COL_ROLE_NAME, role.getRoleName());
        Role repeatRole = roleDao.selectOne(nameQuery);
        if (null != repeatRole) {
            throw new InvalidDataException("角色名称已存在，name="+role.getRoleName()).with(DataECoder.ROLE_REPEATED);
        }
        // 设置默认值
        role.setId(null);
        role.setIsDefault(Role.IS_DEFAULT_FALSE);
        role.setStatus(RoleStatus.NORMAL.getDbValue());
        LocalDateTime nowTime = LocalDateTime.now();
        role.setGmtCreate(nowTime);
        role.setGmtUpdate(nowTime);
        roleDao.insert(role);
        // 新增角色对应权限
        List<Integer> permissionIds = roleDTO.getPermissionIds();
        List<RolePermission> rpList = new ArrayList<>(permissionIds.size());
        for (Integer permissionId : permissionIds) {
            rpList.add(new RolePermission(role.getId(), permissionId));
        }
        rolePermissionDao.deleteByRoleId(role.getId());
        rolePermissionDao.batchInsert(rpList);

        List<PermissionVO> permissions = permissionDao.findListByRoleId(role.getId());
        return new RoleProVO(role, permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleProVO updateRole(RoleDTO roleDTO) throws InvalidDataException {
        Role role = roleDTO.getRole();
        // 检查角色是否存在
        Role oldOne = roleDao.selectById(role.getId());
        if (null == oldOne) {
            throw new InvalidDataException("待修改角色不存在，角色id="+role.getId()).with(DataECoder.ROLE_NOT_EXIST);
        }
        // 检查角色名是否重复
        QueryWrapper<Role> nameQuery = new QueryWrapper<>();
        nameQuery.eq(Role.COL_ROLE_NAME, role.getRoleName());
        Role repeatRole = roleDao.selectOne(nameQuery);
        if (null != repeatRole && !repeatRole.getId().equals(oldOne.getId())) {
            throw new InvalidDataException("角色名称已存在，name="+role.getRoleName()).with(DataECoder.ROLE_REPEATED);
        }
        // 检查是否为默认角色
        if (oldOne.getIsDefault() == Role.IS_DEFAULT_TRUE) {
            throw new InvalidDataException("默认角色禁止修改，id="+role.getId()).with(DataECoder.ROLE_IS_DEFAULT);
        }
        // 更新数据
        oldOne.setRoleName(role.getRoleName());
        oldOne.setGmtUpdate(LocalDateTime.now());
        roleDao.updateById(oldOne);
        // 更新角色对应权限
        List<Integer> permissionIds = roleDTO.getPermissionIds();
        List<RolePermission> rpList = new ArrayList<>(permissionIds.size());
        for (Integer permissionId : permissionIds) {
            rpList.add(new RolePermission(oldOne.getId(), permissionId));
        }
        rolePermissionDao.deleteByRoleId(oldOne.getId());
        rolePermissionDao.batchInsert(rpList);

        List<PermissionVO> permissions = permissionDao.findListByRoleId(oldOne.getId());
        return new RoleProVO(oldOne, permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Integer id) throws InvalidDataException {
        Role oldOne = null;
        // 检查角色是否存在
        if (null != id) {
            oldOne = roleDao.selectById(id);
        }
        if (null == oldOne) {
            throw new InvalidDataException("待删除角色不存在，角色id="+id).with(DataECoder.ROLE_NOT_EXIST);
        }
        // 检查角色是否包含用户
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserRole.COL_ROLE_ID, oldOne.getId());
        UserRole userRole = userRoleDao.selectOne(queryWrapper);
        if (null != userRole) {
            throw new InvalidDataException("角色已关联用户，role id="+id).with(DataECoder.ROLE_HAS_USERS);
        }
        // 删除角色及关联权限
        rolePermissionDao.deleteByRoleId(oldOne.getId());
        roleDao.deleteById(oldOne.getId());
    }
}
