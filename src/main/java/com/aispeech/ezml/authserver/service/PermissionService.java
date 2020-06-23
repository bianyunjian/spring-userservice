package com.aispeech.ezml.authserver.service;

import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.query.PermissionQueryParams;

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


    PagedData<PermissionVO> queryPagedPermissions(PagedParams pagedParams, PermissionQueryParams queryParams);


    PermissionVO addPermission(Permission permission) throws InvalidDataException;

    PermissionVO updatePermission(Permission permission) throws InvalidDataException;


    void deletePermission(Integer id) throws InvalidDataException;


}
