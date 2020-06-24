package com.aispeech.ezml.authserver.support.query;

import com.aispeech.ezml.authserver.support.component.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询参数
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryParams extends QueryParams {

    @Schema(description = "用户姓名，支持模糊查询", example = "管理")
    private String userName;

    @Schema(description = "角色ID", example = "1")
    private String roleId;

}
