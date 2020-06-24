package com.aispeech.ezml.authserver.support.base;

import com.aispeech.ezml.authserver.constant.SysECoder;
import com.aispeech.ezml.authserver.support.ErrorCoder;
import com.aispeech.ezml.authserver.constant.ApiStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * HTTP基础响应类
 *
 * @author ZhangXi
 */
@Schema(description = "通用HTTP响应")
@Data
public class BaseResponse<T> {

    private static final String DEFAULT_MESSAGE = "";

    @Schema(description = "请求结果状态")
    private ApiStatus status;

    @Schema(description = "错误码，用于定位问题", example = "100001")
    private Integer errorCode;

    @Schema(description = "所需数据，可能为空")
    private T data;

    @Schema(description = "请求状态或错误码的补充说明")
    private String message;


    public void success() {
        this.status = ApiStatus.SUCCESS;
        this.errorCode = SysECoder.DEFAULT.get();
    }

    public void success(String message) {
        success();
        this.message = message;
    }

    public void success(String message, T data) {
        success(message);
        this.data = data;
    }

    public void fail() {
        this.status = ApiStatus.FAILURE;
        this.errorCode = SysECoder.INNER_ERROR.get();
    }

    public void fail(String message) {
        fail();
        this.message = message;
    }

    public void fail(String message, ErrorCoder errorCode) {
        fail(message);
        if(null != errorCode) {
            this.errorCode = errorCode.get();
        }
    }


}
