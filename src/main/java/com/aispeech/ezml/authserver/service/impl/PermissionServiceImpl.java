package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.constant.DataECoder;
import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.dao.RolePermissionDao;
import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.model.RolePermission;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.service.CacheService;
import com.aispeech.ezml.authserver.service.PermissionService;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.PermissionQueryParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限服务实现类
 *
 * @author ZhangXi
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private CacheService cacheService;

    @Override
    public List<PermissionVO> getAll() throws InvalidDataException {
        // 查询权限
        List<Permission> permissions = permissionDao.selectList(null);
        if (null == permissions) {
            throw new InvalidDataException("无权限数据！").with(DataECoder.PERMISSION_NOT_EXIST);
        }
        List<PermissionVO> data = new ArrayList<>(permissions.size());
        for (Permission permission : permissions) {
            data.add(new PermissionVO(permission));
        }
        return data;
    }

    @Override
    public PagedData<PermissionVO> queryPagedPermissions(PagedParams pagedParams, PermissionQueryParams queryParams) {
        IPage<Permission> page = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        page = permissionDao.selectPage(page, queryWrapper);
        long total = page.getTotal();
        PagedData<PermissionVO> data = new PagedData<>();
        data.setTotal(total);
        if (total > 0) {
            List<Permission> permissions = page.getRecords();
            List<PermissionVO> voList = new ArrayList<>(permissions.size());
            for (Permission permission : permissions) {
                voList.add(new PermissionVO(permission));
            }
            data.setList(voList);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PermissionVO addPermission(Permission permission) throws InvalidDataException {
        // 检查名称是否重复
        QueryWrapper<Permission> nameQuery = new QueryWrapper<>();
        nameQuery.eq(Permission.COL_PERMISSION_NAME, permission.getPermissionName());
        Permission repeatOne = permissionDao.selectOne(nameQuery);
        if (null != repeatOne) {
            throw new InvalidDataException("权限数据重复,name="+permission.getPermissionName()).with(DataECoder.PERMISSION_REPEATED);
        }
        permissionDao.insert(permission);
        // 更新redis
        cacheService.cachePermission(permission);
        return new PermissionVO(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PermissionVO updatePermission(Permission permission) throws InvalidDataException {
        // 检查权限是否存在
        Permission oldOne = permissionDao.selectById(permission.getId());
        if (null == oldOne) {
            throw new InvalidDataException("待更新权限数据不存在,id="+permission.getId()).with(DataECoder.PERMISSION_NOT_EXIST);
        }
        // 检查权限名称是否重复
        QueryWrapper<Permission> nameQuery = new QueryWrapper<>();
        nameQuery.eq(Permission.COL_PERMISSION_NAME, permission.getPermissionName());
        Permission repeatOne = permissionDao.selectOne(nameQuery);
        if (null != repeatOne && !repeatOne.getId().equals(oldOne.getId())) {
            throw new InvalidDataException("权限数据重复，name="+permission.getPermissionName()).with(DataECoder.PERMISSION_REPEATED);
        }
        // 更新权限
        permissionDao.updateById(permission);
        // 更新redis
        cacheService.cachePermission(permission);
        return new PermissionVO(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePermission(Integer id) throws InvalidDataException {
        // 检查权限数据是否存在
        Permission oldOne = null;
        if (null != id) {
            oldOne = permissionDao.selectById(id);
        }
        if (null == oldOne) {
            throw new InvalidDataException("待删除权限数据不存在,id="+id).with(DataECoder.PERMISSION_NOT_EXIST);
        }
        // 检查权限是否关联角色
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RolePermission.COL_PERMISSION_ID, id);
        RolePermission rp = rolePermissionDao.selectOne(queryWrapper);
        if (null != rp) {
            throw new InvalidDataException("权限已关联角色，permission id="+id).with(DataECoder.PERMISSION_HAS_ROLES);
        }
        // 删除权限
        permissionDao.deleteById(id);
        // 清除redis
        cacheService.cleanPermission(id);
    }
}
