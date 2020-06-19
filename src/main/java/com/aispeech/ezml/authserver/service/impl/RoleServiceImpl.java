package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.dao.RoleDao;
import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.pojo.RoleVO;
import com.aispeech.ezml.authserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色Service层实现类
 *
 * @author ZhangXi
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;



    @Override
    public RoleVO getRoleById(Integer id) {
        Role role = roleDao.selectById(id);
        return new RoleVO(role);
    }
}
