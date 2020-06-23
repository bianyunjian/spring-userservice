package com.aispeech.ezml.authserver.controller;

import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.exception.InvalidParamException;
import com.aispeech.ezml.authserver.model.Permission;
import com.aispeech.ezml.authserver.pojo.PermissionVO;
import com.aispeech.ezml.authserver.service.PermissionService;
import com.aispeech.ezml.authserver.support.AbstractObjectRequest;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.QueryRequest;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import com.aispeech.ezml.authserver.support.query.PermissionQueryParams;
import com.aispeech.ezml.authserver.validation.group.GAdd;
import com.aispeech.ezml.authserver.validation.group.GUpd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
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
    @Operation(summary = "获取全部权限")
    @GetMapping(path = "/all")
    public PermissionListResponse getAll() throws InvalidDataException {
        List<PermissionVO> data = permissionService.getAll();
        PermissionListResponse response = new PermissionListResponse();
        response.success("获取全部权限成功", data);
        return response;
    }

    @Operation(summary = "分页查询权限")
    @PostMapping(path = "/table")
    public PermissionPagedResponse getPagedList(@Validated @RequestBody PermissionQueryRequest request) {
        PagedData<PermissionVO> data = permissionService.queryPagedPermissions(
                request.getPagedParams(), request.getQueryParams());
        PermissionPagedResponse response = new PermissionPagedResponse();
        response.success("分页查询权限列表成功", data);
        return response;
    }

    @Operation(summary = "新增权限")
    @PostMapping(path = "/add")
    public PermissionResponse addPermission(
            @Validated({Default.class, GAdd.class})
            @RequestBody PermissionEditRequest request) throws InvalidDataException {
        PermissionVO data = permissionService.addPermission(request.buildData());
        PermissionResponse response = new PermissionResponse();
        response.success("新增权限成功", data);
        return response;
    }

    @Operation(summary = "修改权限")
    @PostMapping(path = "/update")
    public PermissionResponse updatePermission(
            @Validated({Default.class, GUpd.class})
            @RequestBody PermissionEditRequest request) throws InvalidDataException {
        PermissionVO data = permissionService.updatePermission(request.buildData());
        PermissionResponse response = new PermissionResponse();
        response.success("修改权限成功", data);
        return response;
    }

    @Operation(summary = "删除权限")
    @PostMapping(path = "/delete")
    public BaseResponse deletePermission(
            @Validated @RequestBody PermissionIdRequest request) throws InvalidDataException {
        permissionService.deletePermission(request.getId());
        BaseResponse response = new BaseResponse();
        response.success("删除权限成功");
        return response;
    }


    /*================================================================================================================*/


    @Schema(description = "权限列表响应")
    private static class PermissionListResponse extends BaseResponse<List<PermissionVO>> {}

    @Schema(description = "权限分页查询响应数据")
    private static class PermissionPagedResponse extends BaseResponse<PagedData<PermissionVO>> {}

    @Schema(description = "权限响应数据")
    private static class PermissionResponse extends BaseResponse<PermissionVO> {}

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(description = "权限编辑请求数据")
    private static class PermissionEditRequest extends AbstractObjectRequest<Permission> {

        @Valid
        @Schema(description = "权限数据")
        private PermissionVO data;

        @Override
        protected void validate() throws InvalidParamException {

        }

        @Override
        protected void format() throws InvalidParamException {

        }

        @Override
        protected Permission buildData() {
            return data;
        }
    }

    @Data
    @Schema(description = "权限ID请求数据")
    private static class PermissionIdRequest {
        @NotNull
        @Schema(description = "权限ID", example = "1")
        private Integer id;
    }

    @Schema(description = "权限查询请求数据")
    private static class PermissionQueryRequest extends QueryRequest<PermissionQueryParams> {}

}
