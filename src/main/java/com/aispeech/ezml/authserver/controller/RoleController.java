package com.aispeech.ezml.authserver.controller;

import com.aispeech.ezml.authserver.exception.InvalidParamException;
import com.aispeech.ezml.authserver.pojo.RoleDTO;
import com.aispeech.ezml.authserver.pojo.RoleProVO;
import com.aispeech.ezml.authserver.pojo.RoleVO;
import com.aispeech.ezml.authserver.service.RoleService;
import com.aispeech.ezml.authserver.support.AbstractObjectRequest;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.QueryRequest;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import com.aispeech.ezml.authserver.support.query.RoleQueryParams;
import com.aispeech.ezml.authserver.validation.group.GAdd;
import com.aispeech.ezml.authserver.validation.group.GUpd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.List;

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

    /**
     * 根据ID获取角色
     * @param id 角色ID
     * @return {@link RoleProResponse}
     */
    @Operation(summary = "根据ID获取角色")
    @GetMapping(path = "/{id}")
    public RoleProResponse getOne(
            @Schema(description = "角色ID", example = "1")
            @PathVariable(name = "id") Integer id) {
        RoleProVO data = roleService.getRoleById(id);
        RoleProResponse response = new RoleProResponse();
        response.success("角色数据获取成功", data);
        return response;
    }

    /**
     * 分页查询角色列表
     * @param queryRequest {@link RoleQueryRequest}
     * @return {@link RolePagedResponse}
     */
    @Operation(summary = "分页查询角色列表")
    @PostMapping(path = {"/table"})
    public RolePagedResponse getPagedList(
            @Validated({Default.class, GAdd.class})
            @RequestBody RoleQueryRequest queryRequest) {
        PagedData<RoleProVO> data = roleService.queryPagedRoles(
                queryRequest.getPagedParams(), queryRequest.getQueryParams());
        RolePagedResponse response = new RolePagedResponse();
        response.success("分页查询角色列表成功", data);
        return response;
    }

    /**
     * 新增角色
     * @return {@link RoleProResponse}
     */
    @Operation(summary = "新增角色")
    @PostMapping(path = "/add")
    public RoleProResponse addRole(
            @Validated({Default.class, GAdd.class})
            @RequestBody RoleEditRequest roleEditRequest) {
        RoleProVO data = roleService.addRole(roleEditRequest.buildData());
        RoleProResponse response = new RoleProResponse();
        response.success("新增角色成功", data);
        return response;
    }

    /**
     * 修改角色
     * @return {@link RoleProResponse}
     */
    @Operation(summary = "修改角色")
    @PostMapping(path = "/update")
    public RoleProResponse updateRole(
            @Validated({Default.class, GUpd.class})
            @RequestBody RoleEditRequest roleEditRequest) {
        RoleProVO data = roleService.updateRole(roleEditRequest.buildData());
        RoleProResponse response = new RoleProResponse();
        response.success("修改角色成功", data);
        return response;
    }

    /**
     * 删除角色
     * @return {@link BaseResponse}
     */
    @Operation(summary = "删除角色")
    @PostMapping(path = "/delete")
    public BaseResponse deleteRole(@Validated @RequestBody RoleDelRequest roleDelRequest) {
        roleService.deleteRole(roleDelRequest.getId());
        BaseResponse response = new BaseResponse();
        response.success("删除角色成功");
        return response;
    }

    /*================================================================================================================*/


    @Schema(description = "带权限角色响应数据")
    private static class RoleProResponse extends BaseResponse<RoleProVO> {}

    @Schema(description = "带权限角色分页列表响应数据")
    private static class RolePagedResponse extends BaseResponse<PagedData<RoleProVO>> {}

    @Schema(description = "新增、修改角色请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class RoleEditRequest extends AbstractObjectRequest<RoleDTO> {

        @Schema(description = "角色数据")
        @Valid
        private RoleVO role;

        @NotNull
        @Size(min = 1)
        @Schema(description = "权限标识码数组")
        private List<Integer> codes;

        @Override
        protected void validate() throws InvalidParamException {

        }

        @Override
        protected void format() throws InvalidParamException {

        }

        @Override
        protected RoleDTO buildData() {
            RoleDTO dto = new RoleDTO();
            dto.setRole(this.role);
            dto.setPermissionIds(this.codes);
            return dto;
        }
    }

    @Schema(description = "删除角色请求数据")
    @Data
    private static class RoleDelRequest {
        @NotNull
        private Integer id;
    }

    @Schema(description = "角色分页查询请求数据")
    private static class RoleQueryRequest extends QueryRequest<RoleQueryParams> {}

}
