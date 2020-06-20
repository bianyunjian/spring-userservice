package com.aispeech.ezml.authserver.controller;

import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.service.PermissionService;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限API
 *
 * @author ZhangXi
 */
@Tag(name = "/permission", description = "权限接口")
@Slf4j
@RestController
@RequestMapping(path = {"/permission"})
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 获取全部权限
     * @return {@link PermissionListResponse}
     */
    @Operation(
            summary = "获取全部权限"
    )
    @GetMapping(path = "/all")
    public PermissionListResponse getAll() {
        List<PermissionVO> data = permissionService.getAll();
        PermissionListResponse response = new PermissionListResponse();
        response.success("获取全部权限成功", data);
        return response;
    }

    @Schema(description = "权限列表响应")
    private static class PermissionListResponse extends BaseResponse<List<PermissionVO>> {}

}
