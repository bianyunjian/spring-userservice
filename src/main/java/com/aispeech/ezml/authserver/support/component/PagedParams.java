package com.aispeech.ezml.authserver.support.component;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页参数
 *
 * @author ZhangXi
 */
@Data
public class PagedParams {

    @NotNull
    @Min(1)
    private Integer pageNum;

    private Integer pageSize;

}
