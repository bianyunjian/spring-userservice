package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.model.User;

/**
 * 缓存服务接口
 * 功能：将权限与用户数据缓存到redis中
 *
 * @author ZhangXi
 */
public interface CacheService {


    boolean cacheUser(User user);


    boolean cachePermission(Permission permission);


    boolean cleanUser(Integer id);


    boolean cleanPermission(Integer id);


    boolean refreshUsers();

    boolean refreshPermissions();

}
