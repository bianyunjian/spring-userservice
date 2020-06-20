package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.service.PermissionService;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PermissionVO> getAll() {
        List<Permission> permissions = permissionDao.selectList(null);
        if (null == permissions) {
            return null;
        }
        List<PermissionVO> data = new ArrayList<>(permissions.size());
        for (Permission permission : permissions) {
            data.add(new PermissionVO(permission));
        }
        return data;
    }
}
