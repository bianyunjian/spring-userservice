package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.pojo.PermissionVO;

import java.util.List;

/**
 * 权限服务接口
 *
 * @author ZhangXi
 */
public interface PermissionService {

    /**
     * 获取全部权限
     * @return {@link List<PermissionVO>}
     */
    List<PermissionVO> getAll() throws InvalidDataException;

}
