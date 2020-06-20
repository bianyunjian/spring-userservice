package com.aispeech.ezml.authserver.support;

import com.aispeech.ezml.authserver.support.base.BaseRequest;
import com.aispeech.ezml.authserver.support.component.OrderParams;
import com.aispeech.ezml.authserver.support.component.PagedParams;
import com.aispeech.ezml.authserver.support.component.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;

/**
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRequest<K extends QueryParams> extends BaseRequest {

    /**
     * 分页参数
     */
    @Valid
    @Schema(description = "分页参数", required = true)
    private PagedParams pagedParams;

    /**
     * 排序参数
     */
    @Schema(description = "排序参数")
    private OrderParams orderParams;

    /**
     * 查询参数
     */
    @Schema(description = "查询参数")
    private K queryParams;

}
