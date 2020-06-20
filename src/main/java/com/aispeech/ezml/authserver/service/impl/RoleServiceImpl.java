package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.dao.RoleDao;
import com.aispeech.ezml.authserver.dao.RolePermissionDao;
import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.model.RolePermission;
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



    @Override
    public RoleProVO getRoleById(Integer id) {
        Role role = roleDao.selectById(id);
        if (null == role) {
            return null;
        }
        List<PermissionVO> list = permissionDao.findListByRoleId(id);
        return new RoleProVO(role, list);
    }

    @Override
    public PagedData<RoleProVO> queryPagedRoles(PagedParams pagedParams, RoleQueryParams queryParams) {
        IPage<Role> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<Role> wQuery = new QueryWrapper<>();
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
    public RoleProVO addRole(RoleDTO roleDTO) {
        // todo 前置处理：检查是否有重复，检查是否唯一等
        Role role = roleDTO.getRole();
        roleDao.insert(role);
        List<Integer> permissionIds = roleDTO.getPermissionIds();
        List<RolePermission> rpList = new ArrayList<>(permissionIds.size());
        for (Integer permissionId : permissionIds) {
            rpList.add(new RolePermission(role.getId(), permissionId));
        }
        rolePermissionDao.batchInsert(rpList);
        List<PermissionVO> permissions = permissionDao.findListByRoleId(role.getId());
        return new RoleProVO(role, permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleProVO updateRole(RoleDTO roleDTO) {
        // todo 前置处理：检查是否存在，检查是否唯一等
        Role role = roleDTO.getRole();
        Role oldOne = roleDao.selectById(role.getId());
        if (null == oldOne) {
            // todo 抛出异常
        }
        roleDao.updateById(role);
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
    public void deleteRole(Integer id) {
        Role oldOne = roleDao.selectById(id);
        if (null != oldOne) {
            rolePermissionDao.deleteByRoleId(oldOne.getId());
            roleDao.deleteById(oldOne.getId());
        }
    }
}
