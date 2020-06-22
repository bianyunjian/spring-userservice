package com.aispeech.ezml.authserver.controller;

import com.aispeech.ezml.authserver.exception.InvalidParamException;
import com.aispeech.ezml.authserver.pojo.UserDTO;
import com.aispeech.ezml.authserver.pojo.UserInfoVO;
import com.aispeech.ezml.authserver.pojo.UserProVO;
import com.aispeech.ezml.authserver.pojo.UserVO;
import com.aispeech.ezml.authserver.service.UserService;
import com.aispeech.ezml.authserver.support.AbstractObjectRequest;
import com.aispeech.ezml.authserver.support.PagedData;
import com.aispeech.ezml.authserver.support.QueryRequest;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import com.aispeech.ezml.authserver.support.query.UserQueryParams;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 用户API
 *
 * @author ZhangXi
 */
@Tag(name = "/user", description = "用户接口")
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "根据用户名获取用户信息")
    @PostMapping(path = "/getUserInfo")
    public UserInfoResponse getUserInfo(@Validated @RequestBody UserInfoRequest request) {
        String userName = request.getUserName();
        UserInfoVO data = userService.getUserInfoByLoginName(userName);
        UserInfoResponse response = new UserInfoResponse();
        response.success("获取用户信息成功", data);
        return response;
    }

    @Operation(summary = "根据ID获取用户")
    @GetMapping(path = "/{id}")
    public UserProResponse getOne(@PathVariable("id") @NotNull Integer id) {
        UserProVO data = userService.getUserById(id);
        UserProResponse response = new UserProResponse();
        response.success("获取用户数据成功", data);
        return response;
    }

    @Operation(summary = "分页查询用户列表")
    @PostMapping(path = "/table")
    public UserPagedResponse getPagedList(@Validated @RequestBody UserQueryRequest request) {
        PagedData<UserProVO> data = userService.queryPagedUsers(request.getPagedParams(), request.getQueryParams());
        UserPagedResponse response = new UserPagedResponse();
        response.success("分页查询用户数据成功", data);
        return response;
    }

    @Operation(summary = "新增用户")
    @PostMapping(path = "/add")
    public UserProResponse addUser(
            @Validated({Default.class, GAdd.class})
            @RequestBody UserEditRequest request) {
        UserProVO data = userService.addUser(request.buildData());
        UserProResponse response = new UserProResponse();
        response.success("新增用户成功", data);
        return response;
    }

    @Operation(summary = "修改用户")
    @PostMapping(path = "/update")
    public UserProResponse updateUser(
            @Validated({Default.class, GUpd.class})
            @RequestBody UserEditRequest request) {
        UserProVO data = userService.updateUser(request.buildData());
        UserProResponse response = new UserProResponse();
        response.success("修改用户成功", data);
        return response;
    }

    @Operation(summary = "删除用户")
    @PostMapping(path = "/delete")
    public BaseResponse deleteUser(@Validated @RequestBody UserDelRequest request) {
        userService.deleteUser(request.getId());
        BaseResponse response = new BaseResponse();
        response.success("删除用户成功");
        return response;
    }


    /*================================================================================================================*/

    @Data
    private static class UserInfoRequest {
        @NotBlank
        private String userName;
    }

    @Schema(description = "用户信息响应数据")
    private static class UserInfoResponse extends BaseResponse<UserInfoVO> {}

    @Schema(description = "带角色用户响应数据")
    private static class UserProResponse extends BaseResponse<UserProVO> {}

    @Schema(description = "带角色用户分页列表数据")
    private static class UserPagedResponse extends BaseResponse<PagedData<UserProVO>> {}

    @Schema(description = "用户分页查询请求")
    private static class UserQueryRequest extends QueryRequest<UserQueryParams> {}


    @Schema(description = "用户编辑请求")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class UserEditRequest extends AbstractObjectRequest<UserDTO> {

        @Valid
        @Schema(description = "用户数据")
        private UserVO user;

        @NotNull
        @Min(1)
        @Schema(description = "角色ID")
        private Integer roleId;

        @Override
        protected void validate() throws InvalidParamException {

        }

        @Override
        protected void format() throws InvalidParamException {

        }

        @Override
        protected UserDTO buildData() {
            UserDTO dto = new UserDTO();
            dto.setUser(this.user);
            dto.setRoleId(this.roleId);
            return dto;
        }
    }

    @Schema(description = "用户删除请求")
    @Data
    private static class UserDelRequest {
        @NotNull
        private Integer id;
    }

}
