package com.aispeech.ezml.authserver.controller;

import com.aispeech.ezml.authserver.model.Role;
import com.aispeech.ezml.authserver.pojo.RoleVO;
import com.aispeech.ezml.authserver.service.RoleService;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色API
 *
 * @author ZhangXi
 */
@Tag(name = "/role", description = "角色接口")
@RestController
@RequestMapping(path = "/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @Operation(
            summary = "根据ID获取角色"
    )
    @GetMapping(path = "/{id}")
    public RoleResponse get(@Schema(description = "角色ID", example = "1") @PathVariable(name = "id") Integer id) {
        RoleVO data = roleService.getRoleById(id);
        RoleResponse response = new RoleResponse();
        response.success("角色数据获取成功", data);
        return response;
    }









    @Schema(description = "角色请求响应")
    private static class RoleResponse extends BaseResponse<RoleVO> {}









}
