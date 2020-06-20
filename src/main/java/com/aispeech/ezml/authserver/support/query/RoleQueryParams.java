package com.aispeech.ezml.authserver.support.query;

import com.aispeech.ezml.authserver.support.component.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询参数
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色分页查询参数")
@Data
public class RoleQueryParams extends QueryParams {
    //fixme 检查是否有特定参数
}
