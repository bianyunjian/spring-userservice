package com.aispeech.ezml.authserver.service.impl;

import com.aispeech.ezml.authserver.dao.PermissionDao;
import com.aispeech.ezml.authserver.dao.UserDao;
import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.model.User;
import com.aispeech.ezml.authserver.pojo.UserCacheVO;
import com.aispeech.ezml.authserver.service.CacheService;
import com.aispeech.ezml.authserver.tool.JsonTool;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存服务实现类
 *
 * @author ZhangXi
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    private static final String KEY_PREFIX_USER = "USER_";
    private static final String KEY_PREFIX_PERMISSION = "PERMISSION_";

    private static final SimpleFilterProvider USER_FILTER_PROVIDER = null;

    @Resource
    private UserDao userDao;
    @Resource
    private PermissionDao permissionDao;
    @Autowired
    private StringRedisTemplate redisTemplate;



    @Override
    public boolean cacheUser(User user) {
        if (null == user || user.getId() == null) {
            return false;
        }
        try {
            String value = JsonTool.objToJson(new UserCacheVO(user));
            String key = KEY_PREFIX_USER + user.getId().toString();
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (JsonTool.JsonSerializeException e) {
            log.error("用户序列化失败", e);
            return false;
        }
    }

    @Override
    public boolean cachePermission(Permission permission) {
        if (null == permission || permission.getId() == null) {
            return false;
        }
        try {
            String key = KEY_PREFIX_PERMISSION + permission.getId().toString();
            String value = JsonTool.objToJson(permission);
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (JsonTool.JsonSerializeException e) {
            log.error("权限数据序列化失败", e);
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean cleanUser(Integer id) {
        if (null == id) {
            return false;
        }
        String key = KEY_PREFIX_USER + id.toString();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            return redisTemplate.delete(key);
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean cleanPermission(Integer id) {
        if (null == id) {
            return false;
        }
        String key = KEY_PREFIX_PERMISSION + id.toString();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            return redisTemplate.delete(key);
        }
        return true;
    }

    @Override
    public boolean refreshUsers() {
        List<User> list = userDao.selectList(null);
        List<User> failList = new ArrayList<>();
        if (null != list) {
            for (User user : list) {
                boolean cached = cacheUser(user);
                if (!cached) {
                    failList.add(user);
                }
            }
        }
        if (!failList.isEmpty()) {
            //todo 其他处理
            return false;
        }
        return true;
    }

    @Override
    public boolean refreshPermissions() {
        List<Permission> permissions = permissionDao.selectList(null);
        List<Permission> failList = new ArrayList<>();
        if (null != permissions) {
            for (Permission permission : permissions) {
                boolean cached = cachePermission(permission);
                if (!cached) {
                    failList.add(permission);
                }
            }
        }
        if (!failList.isEmpty()) {
            //todo 其他处理
            return false;
        }
        return true;
    }
}
