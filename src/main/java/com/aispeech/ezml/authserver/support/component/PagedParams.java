package com.aispeech.ezml.authserver.support.component;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页参数
 *
 * @author ZhangXi
 */
@Schema(description = "分页参数")
@Data
public class PagedParams {

    @NotNull
    @Min(1)
    @Schema(description = "当前页码", example = "1")
    private Integer pageNum;

    @NotNull
    @Min(1)
    @Schema(description = "每页数据量", example = "10")
    private Integer pageSize;

}
