package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.constant.DataECoder;
import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.exception.InvalidDataException;
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
    public List<PermissionVO> getAll() throws InvalidDataException {
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
}
